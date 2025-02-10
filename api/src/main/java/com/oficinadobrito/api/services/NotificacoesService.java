package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Notificacao;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.NotificacaoRepository;
import com.oficinadobrito.api.utils.dtos.notificacao.UpdateNotificacaoDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Service
public class NotificacoesService extends GenericService<Notificacao> {

    private final NotificacaoRepository notificacaoRepository;
    private  final UsuariosService usuariosService;

    public NotificacoesService(NotificacaoRepository repository, UsuariosService usuariosService, NotificacaoRepository notificacaoRepository) {
        super(repository);
        this.usuariosService = usuariosService;
        this.notificacaoRepository = notificacaoRepository;
    }

    public Set<Notificacao> getAllNotificationForUsuario(String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.notificacaoRepository.findByUsuarioId(usuarioId));
    }
    
    public Notificacao updateById(BigInteger notificacaoId, UpdateNotificacaoDto notificacao){
        Notificacao resource = this.findById(notificacaoId);

        if (notificacao.assunto() != null) {
            resource.setAssunto(notificacao.assunto());
        }
        if (notificacao.descricao() != null) {
            resource.setDescricao(notificacao.descricao());
        }
        if (notificacao.status() != null) {
            resource.setStatus(notificacao.status());
        }

        return this.save(resource);
    }
}
