package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.AnuncioRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AnunciosService extends GenericService<Anuncio>{

    private final UsuariosService usuariosService;
    private final AnuncioRepository anuncioRepository;

    public AnunciosService(AnuncioRepository repository,UsuariosService usuariosService,AnuncioRepository anuncioRepository) {
        super(repository);
        this.usuariosService = usuariosService;
        this.anuncioRepository = anuncioRepository;
    }

    public Optional<Anuncio> findAnuncioForId(BigInteger id){
        return super.repository.findById(id);
    }

    public Set<Anuncio> getAllAnunciosForUsuario(String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.anuncioRepository.findByUsuarioId(usuarioId));
    }
}
