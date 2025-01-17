package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Notificacao;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.NotificacaoRepository;
import org.springframework.stereotype.Service;
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
        Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.notificacaoRepository.findByUsuarioId(usuarioId));
    }
}
