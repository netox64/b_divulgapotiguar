package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.repositories.AnuncioRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class AnunciosService extends GenericService<Anuncio>{

    public AnunciosService(AnuncioRepository repository) {
        super(repository);
    }

    public Optional<Anuncio> findAnuncioForId(BigInteger id){
        return super.repository.findById(id);
    }
}
