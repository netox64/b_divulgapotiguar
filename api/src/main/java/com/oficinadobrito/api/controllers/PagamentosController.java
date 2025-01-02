package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Pagamento;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.PagamentosService;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.pagamento.CreatePagamentoDto;
import com.oficinadobrito.api.utils.dtos.pagamento.UpdatePagamentoDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("pagamentos")
public class PagamentosController implements IController<Pagamento, CreatePagamentoDto, UpdatePagamentoDto> {

    private final PagamentosService pagamentosService;
    private final UsuariosService usuariosService;

    public PagamentosController(PagamentosService pagamentosService,UsuariosService usuariosService){
        this.pagamentosService = pagamentosService;
        this.usuariosService = usuariosService;
    }

    @PostMapping()
    @Override
    public ResponseEntity<?> postResource(@RequestBody @Valid CreatePagamentoDto resource) {
        Pagamento novo = Pagamento.createDtoToEntity(resource);
        Usuario usuario = this.usuariosService.findUsuarioForId(resource.usuarioId());
        novo.setUsuario(usuario);
        var pagamento = this.pagamentosService.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Pagamento>> getAllResource() {
        List<Pagamento> pagamentoList = StreamSupport.stream(this.pagamentosService.findAll().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok(pagamentoList);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getResourceById(@PathVariable("id") BigInteger id) {
        Pagamento pagamento = this.pagamentosService.findById(id);
        return  ResponseEntity.ok(pagamento);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Pagamento> updateResource(@PathVariable("id") BigInteger id,@RequestBody @Valid UpdatePagamentoDto resource) {
        Pagamento pagamentoUpdate = Pagamento.updateDtoToEntity(resource);
        Pagamento pagamento = this.pagamentosService.updateById(id,pagamentoUpdate);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.pagamentosService.delete(id);
        return ResponseEntity.ok().build();
    }
}
