package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Pagamento;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PagamentosService extends GenericService<Pagamento> {

    private final PagamentoRepository pagamentoRepository;
    private final UsuariosService usuariosService;
    public PagamentosService(PagamentoRepository repository,PagamentoRepository pagamentoRepository,UsuariosService usuariosService) {
         super(repository);
         this.pagamentoRepository = pagamentoRepository;
         this.usuariosService = usuariosService;
    }

    public Set<Pagamento> getAllPagamentosForUsuario(String usuarioId){
       Usuario usuario = this.usuariosService.findUsuarioForId(usuarioId);
       return usuario == null? new HashSet<>() : new HashSet<>(this.pagamentoRepository.findByUsuarioId(usuarioId));
    }
}
