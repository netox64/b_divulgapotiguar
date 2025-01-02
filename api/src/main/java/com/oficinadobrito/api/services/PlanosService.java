package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.PlanoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlanosService extends GenericService<Plano> {

    private final PlanoRepository planosRepository;
    private final UsuariosService usuariosService;
    public PlanosService(PlanoRepository repository, PlanoRepository planosRepository, UsuariosService usuariosService) {
        super(repository);
        this.planosRepository= planosRepository;
        this.usuariosService = usuariosService;
    }

    public Set<Plano> getAllPlanosForUsuario(String usuarioId){
       Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
       return usuario == null? new HashSet<>() : new HashSet<Plano>(this.planosRepository.findByUsuarioId(usuarioId));
    }
}
