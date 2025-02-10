package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.entities.Notificacao;
import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.utils.dtos.usuario.UpdateUsuarioDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ServenteService {
    
    private final NotificacoesService notificacoesService;
    private final PlanosService planosService;
    private final UsuariosService usuariosService;
    private final PasswordEncoder passwordEncoder;
    
    public ServenteService(NotificacoesService notificacoesService, PlanosService planosService, UsuariosService usuariosService,PasswordEncoder passwordEncoder){
         this.notificacoesService=notificacoesService;
         this.planosService=planosService;
         this.usuariosService=usuariosService;
         this.passwordEncoder = passwordEncoder;
    }
    
    public Usuario updateUsuario(String id, UpdateUsuarioDto usuarioUpdate) {
        Usuario usuarioExistente = this.usuariosService.findUsuarioById(id);
        if (usuarioUpdate.password() != null) {
            if(!this.passwordEncoder.matches(usuarioUpdate.password(),usuarioExistente.getPassword())){
                throw new BadRequestException("user password is incorrect");
            }
            usuarioExistente.setPassword(usuarioUpdate.password());
        }
        if (usuarioUpdate.username() != null) {
            boolean userExists = this.usuariosService.usuarioThisUsernameExists(usuarioUpdate.username());
            boolean usuarioDiferenteDoLogado = !(usuarioExistente.getUsername().equalsIgnoreCase(usuarioUpdate.username()));
            if (userExists && usuarioDiferenteDoLogado ){
                throw new BadRequestException("There is already a user with this username in the application");
            }
            usuarioExistente.setUsername(usuarioUpdate.username());
        }
        if (usuarioUpdate.image() != null) {
            usuarioExistente.setImage(usuarioUpdate.image());
        }
        if (usuarioUpdate.phone() != null) {
            usuarioExistente.setPhone(usuarioUpdate.phone());
        }
        if (usuarioUpdate.token() != null) {
            usuarioExistente.setToken(usuarioUpdate.token());
        }
        if (usuarioUpdate.notificacoes() != null && usuarioUpdate.notificacoes().length > 0) {
            if(usuarioUpdate.planos()[0].compareTo(BigInteger.ZERO) != 0) {
                for (BigInteger idNotificacao : usuarioUpdate.notificacoes()) {
                    Notificacao notificacao = this.notificacoesService.findById(idNotificacao);
                    usuarioExistente.addNotificacao(notificacao);
                }
            }
        }
        if (usuarioUpdate.planos() != null && usuarioUpdate.planos().length > 0) {
            if(usuarioUpdate.planos()[0].compareTo(BigInteger.ZERO) != 0){
                for (BigInteger idPlano : usuarioUpdate.planos()) {
                    Plano plano = this.planosService.findById(idPlano);
                    usuarioExistente.addPlano(plano);
                }
            }
        }
        if (usuarioUpdate.role() != null) {
            usuarioExistente.setRole(usuarioUpdate.role());
            this.atualizarAuthoritiesDoUsuarioAutenticado(usuarioExistente);
        }
        return this.usuariosService.save(usuarioExistente);
    }

    private void atualizarAuthoritiesDoUsuarioAutenticado(Usuario usuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}
