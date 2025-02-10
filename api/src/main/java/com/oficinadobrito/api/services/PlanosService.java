package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.PlanoRepository;
import com.oficinadobrito.api.utils.dtos.plano.UpdatePlanoDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Service
public class PlanosService extends GenericService<Plano> {

    private final PlanoRepository planosRepository;
    private final UsuariosService usuariosService;

    public PlanosService(PlanoRepository repository, PlanoRepository planosRepository,UsuariosService usuariosService) {
        super(repository);
        this.planosRepository= planosRepository;
        this.usuariosService = usuariosService;
    }

    public Set<Plano> getAllPlanosForUsuario(String usuarioId){
       Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
       return usuario == null? new HashSet<>() : new HashSet<>(this.planosRepository.findByUsuarioId(usuarioId));
    }

    public Plano createPlano(Plano plano, String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        plano.setUsuario(usuario);
        return this.save(plano);
    }

    public Plano updateById(BigInteger planoId, UpdatePlanoDto updatePlanoDto) {
        Plano planoAtualizar = this.findById(planoId);

        if (updatePlanoDto.nome() != null && !updatePlanoDto.nome().isEmpty()) {
            planoAtualizar.setNome(updatePlanoDto.nome());
        }
        if (updatePlanoDto.valor() > 0) {
            planoAtualizar.setValor(updatePlanoDto.valor());
        }
        if (updatePlanoDto.quantAnuncio() >= 0 && updatePlanoDto.quantAnuncio() <= 120) {
            planoAtualizar.setQuantAnuncio(updatePlanoDto.quantAnuncio());
        }
        if (updatePlanoDto.duracao() >= 0 && updatePlanoDto.duracao() <= 365) {
            planoAtualizar.setDuracao(updatePlanoDto.duracao());
        }
        if (updatePlanoDto.usuarioId() != null && updatePlanoDto.usuarioId().length() == 36) {
            planoAtualizar.setUsuario(this.usuariosService.findUsuarioById(updatePlanoDto.usuarioId()));
        }

        return this.save(planoAtualizar);
    }

}
