package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.repositories.ImovelRepository;
import org.springframework.stereotype.Service;

@Service
public class ImoveisService extends GenericService<Imovel> {

    public ImoveisService(ImovelRepository repository) {
        super(repository);
    }
}
