package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.services.PlanosService;
import com.oficinadobrito.api.utils.dtos.plano.CreatePlanoDto;
import com.oficinadobrito.api.utils.dtos.plano.UpdatePlanoDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("planos")
public class PlanosController implements IController<Plano, CreatePlanoDto, UpdatePlanoDto> {

    private final PlanosService planosService;

    public PlanosController(PlanosService planosService){
        this.planosService = planosService;
    }

    @PostMapping()
    @Override
    public ResponseEntity<Plano> postResource(@RequestBody @Valid CreatePlanoDto resource) {
        Plano novo = Plano.createDtoToEntity(resource);
        var pagamento = this.planosService.save(novo);
        return ResponseEntity.ok(pagamento);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Plano>> getAllResource() {
        List<Plano> pagamentoList = StreamSupport.stream(this.planosService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(pagamentoList);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Plano> getResourceById(@PathVariable("id") BigInteger id) {
        Plano plano = this.planosService.findById(id);
        return  ResponseEntity.ok(plano);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Plano> updateResource(@PathVariable("id") BigInteger id,@RequestBody @Valid UpdatePlanoDto resource) {
        Plano planoUpdate = Plano.updateDtoToEntity(resource);
        Plano plano = this.planosService.updateById(id,planoUpdate);
        return  ResponseEntity.ok(plano);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.planosService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/anuncios")
    public ResponseEntity<Set<Anuncio>> getAnunciosOfPlanoById(@PathVariable("id") BigInteger id) {
        Plano plano = this.planosService.findById(id);
        return  ResponseEntity.ok(plano.getAnuncios());
    }
}
