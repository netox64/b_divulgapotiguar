package com.oficinadobrito.api.services;

import com.oficinadobrito.api.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    public AuthenticationService(UsuarioRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> user = this.userRepository.findByEmail(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
