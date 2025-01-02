package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository  extends IGenericRepository<Anuncio> {
}
