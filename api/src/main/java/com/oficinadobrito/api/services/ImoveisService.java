package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.ImovelRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ImoveisService extends GenericService<Imovel> {

    private final UsuariosService usuariosService;
    private final ImovelRepository imoveisRepository;

    public ImoveisService(ImovelRepository repository,UsuariosService usuariosService,ImovelRepository imoveisRepository) {
        super(repository);
        this.usuariosService = usuariosService;
        this.imoveisRepository = imoveisRepository;

    }

    public Set<Imovel> getAllImoveisForUsuario(String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.imoveisRepository.findByUsuarioId(usuarioId));
    }
}
