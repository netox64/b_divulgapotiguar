package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.services.PlanosService;
import com.oficinadobrito.api.utils.dtos.plano.CreatePlanoDto;
import com.oficinadobrito.api.utils.dtos.plano.UpdatePlanoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("planos")
public class PlanosController implements IController<Plano, CreatePlanoDto, UpdatePlanoDto> {

    private final PlanosService planosService;

    public PlanosController(PlanosService planosService) {
        this.planosService = planosService;
    }

    @Operation(summary = "create one plan - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping()
    @Override
    public ResponseEntity<Plano> postResource(@RequestBody @Valid CreatePlanoDto resource) {
        Plano novo = Plano.createDtoToEntity(resource);
        var pagamento = this.planosService.createPlano(novo, resource.usuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @Operation(summary = "get all plans")
    @PermitAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Plano>> getAllResource() {
        List<Plano> planosList = StreamSupport.stream(this.planosService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(planosList);
    }

    @Operation(summary = "get one plan by id - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Plano> getResourceById(@PathVariable("id") BigInteger id) {
        Plano plano = this.planosService.findById(id);
        return ResponseEntity.ok(plano);
    }

    @Operation(summary = "update one plan -  (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Plano> updateResource(@PathVariable("id") BigInteger id,
            @RequestBody @Valid UpdatePlanoDto resource) {
        Plano plano = this.planosService.updateById(id, resource);
        return ResponseEntity.ok(plano);
    }

    @Operation(summary = "delete one plan -  (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.planosService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "get all ads for a plan, by planoId -  (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/anuncios")
    public ResponseEntity<Set<Anuncio>> getAnunciosOfPlanoById(@PathVariable("id") BigInteger id) {
        Plano plano = this.planosService.findById(id);
        return ResponseEntity.ok(plano.getAnuncios());
    }
}
