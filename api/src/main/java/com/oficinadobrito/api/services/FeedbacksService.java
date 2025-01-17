package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Feedback;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.FeedbackRepository;
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

    public Feedback sendFeedbackUsuario(String usuarioId, Feedback feedback){
        Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
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
        Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.feedbackRepository.findByUsuarioId(usuarioId));
    }

}
