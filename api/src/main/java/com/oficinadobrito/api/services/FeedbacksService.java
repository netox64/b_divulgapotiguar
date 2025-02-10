package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Feedback;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.FeedbackRepository;
import com.oficinadobrito.api.utils.dtos.feedback.UpdateFeedbackDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Service
public class FeedbacksService extends GenericService<Feedback> {

    private final FeedbackRepository feedbackRepository;
    private final UsuariosService usuariosService;
    private final AnunciosService anunciosService;

    public FeedbacksService(FeedbackRepository repository,UsuariosService usuariosService,AnunciosService anunciosService,FeedbackRepository feedbackRepository) {
        super(repository);
        this.usuariosService = usuariosService;
        this.anunciosService = anunciosService;
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback sendFeedbackUsuario(String usuarioId, Feedback feedback) {
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        Set<Feedback> feedbacks = usuario.getFeedbacks();

        // Verifica se há feedbacks existentes,se não existir retorna 0,se existir chama no stream ,somando tudo
        double somaTotal = feedbacks.isEmpty() ? 0 : feedbacks.stream().mapToDouble(Feedback::getStars).sum();
        int totalFeedbacks = feedbacks.size();

        // Atualizar total de estrelas e média do usuário
        double somaTotalFeedbacks = somaTotal + feedback.getStars();
        int novoTotalFeedbacks = totalFeedbacks + 1;
        double media = somaTotalFeedbacks / novoTotalFeedbacks;
        usuario.setMediaStars(media);
        this.usuariosService.save(usuario);

        // Associar feedback ao usuário e salvar
        feedback.setUsuario(usuario);
        super.repository.save(feedback);

        return feedback;
    }


    public Feedback sendFeedbackAnuncio(BigInteger anuncioId, Feedback feedback){
        Anuncio anuncio = this.anunciosService.findAnuncioForId(anuncioId).orElseThrow(() -> new ResourceNotFoundException("Usuario with this id not exists"));
        feedback.setAnuncio(anuncio);
        super.repository.save(feedback);
        return feedback;
    }

    public Set<Feedback> getAllFeedbacksForUsuario(String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.feedbackRepository.findByUsuarioId(usuarioId));
    }
    public Feedback updateById(BigInteger feedbackId, UpdateFeedbackDto feedback) {
        Feedback feedbackAtualizar = this.findById(feedbackId);

        if (!feedback.usuarioRequestId().isBlank()) {
            feedbackAtualizar.setUsuario(this.usuariosService.findUsuarioById(feedback.usuarioRequestId()));
        }
        if (feedback.remetenteUsername() != null) {
            feedbackAtualizar.setRemetenteUsername(feedback.remetenteUsername());
        }
        if (feedback.stars() >= 0 && feedback.stars() <= 5) {
            feedbackAtualizar.setStars(feedback.stars());
        }
        if (feedback.comentario() != null) {
            feedbackAtualizar.setComentario(feedback.comentario());
        }
        if (feedback.usuarioId() != null) {
            feedbackAtualizar.setUsuario(this.usuariosService.findUsuarioById(feedback.usuarioRequestId()));
        }
        if (feedback.anuncioId() != null && feedback.anuncioId().compareTo(BigInteger.ZERO) > 0) {
            feedbackAtualizar.setAnuncio(this.anunciosService.findById(feedback.anuncioId()));
        }

        return this.save(feedbackAtualizar);
    }


}
