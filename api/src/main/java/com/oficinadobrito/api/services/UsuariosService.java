package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.repositories.UsuarioRepository;
import com.oficinadobrito.api.utils.validators.EmailValidator;
import com.oficinadobrito.api.utils.dtos.usuario.LoginDto;
import com.oficinadobrito.api.utils.dtos.usuario.VerifyTokenPassword;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.oficinadobrito.api.entities.Usuario;

@Service
public class UsuariosService {

    private final UsuarioRepository usuariosRepository;
    private final JwtService jwtService;
    private final TokenEmailService tokenEmailService;
    private final AuthenticationManager authenticationManager;
    private final EmailsService emailsService;

    public UsuariosService(JwtService jwtService, AuthenticationManager authenticationManager, UsuarioRepository usuariosRepository, TokenEmailService tokenEmailService, EmailsService emailsService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usuariosRepository = usuariosRepository;
        this.tokenEmailService = tokenEmailService;
        this.emailsService = emailsService;
    }

    public Usuario newUsuario(Usuario user) {
        Optional<UserDetails> usuario = this.usuariosRepository.findByEmail(user.getEmail());
        if (!EmailValidator.isValidEmail(user.getEmail()) || usuario.isPresent()) {
            throw new BadRequestException("The email provided is in the wrong format or a user with that email is already in this application");
        }
        return this.usuariosRepository.save(user);
    }

    public String authenticarUsuario(LoginDto usuario) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usuario.email(), usuario.password());
        try {
            Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
            return jwtService.generateToken((Usuario) authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            return "";
        } catch (AuthenticationException e) {
            return ""; // Outras exceções de autenticação
        }
    }

    /* The method a seguir fazem parte da logica de redefinição de senhas via email na qual tem as etapas
     * -  1 Usuario pede para redefinir senha e envia o email que era dele no softaware -> sendHash
     * -  2 sendHash  retorna um rash
     * -  3  O usuario de posse do hash deve inserir  o hash fazer POST
     *    se o rash for verdadeiro verifyHash
     *    autoriza para redefinir senha
     * */

    public boolean sendHash(String email) {
        Optional<Usuario> user = this.usuariosRepository.findUsuarioByEmail(email);
        if (user.isPresent()) {
            String token = null;
            try {
                token = this.tokenEmailService.encrypt(email);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.emailsService.enviarEmail(email, "Use o código para redefinir sua senha na aplicação " + "Divulga Potiguar", token);
            return true;
        }
        return false;
    }

    public VerifyTokenPassword verifyHash(String token) {
        String email = null;
        try {
            email = this.tokenEmailService.decrypt(token);

            if (this.tokenEmailService.validateToken(token, email)) {
                return new VerifyTokenPassword(email, true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new VerifyTokenPassword("false.@gmail.com", false);
    }

    public boolean redefinirSenha(String email, String novaSenha) {
        Optional<Usuario> user = this.usuariosRepository.findUsuarioByEmail(email);
        if (user.isPresent()) {
            Usuario atualizado = user.get();
            atualizado.setPassword(novaSenha);
            this.usuariosRepository.save(atualizado);
            return true;
        }
        return false;
    }

    public Usuario findUsuarioForId(String id) {
        return this.usuariosRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not exists Usuario with this id"));
    }

    public Usuario updateUsuario(String id, Usuario usuarioUpdate) {
        Usuario usuarioExistente = this.usuariosRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not exists Usuario with this id"));

        if(usuarioUpdate.getPassword() != null){
            usuarioExistente.setPassword(usuarioUpdate.getPassword());
        }
        else if(usuarioUpdate.getRole() != null){
            usuarioExistente.setRole(usuarioUpdate.getRole());
        }
        return this.usuariosRepository.save(usuarioExistente);
    }

    public List<Usuario> getAll(){
        return this.usuariosRepository.findAll();
    }

    public Usuario findUsuarioById(String id){
        return this.usuariosRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Usuario with this id not exists"));
    }

}
