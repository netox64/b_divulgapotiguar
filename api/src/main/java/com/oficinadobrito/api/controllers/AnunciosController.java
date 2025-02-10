package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Categoria;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.services.AnunciosService;
import com.oficinadobrito.api.utils.dtos.anuncio.CreateAnuncioDto;
import com.oficinadobrito.api.utils.dtos.anuncio.UpdateAnuncioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("anuncios")
public class AnunciosController implements IController<Anuncio, CreateAnuncioDto, UpdateAnuncioDto> {

    private final AnunciosService anunciosServices;

    public AnunciosController(AnunciosService anunciosServices) {
        this.anunciosServices = anunciosServices;
    }

    @Operation(summary = "create one anuncio - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping()
    @Override
    public ResponseEntity<Anuncio> postResource(@RequestBody @Valid CreateAnuncioDto resource) {
        Anuncio anuncio = Anuncio.createDtoToEntity(resource);
        var anuncioCriado = this.anunciosServices.createAnuncio(anuncio,resource.imovelId(),resource.usuarioId(),resource.planoId(),resource.categoriasIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(anuncioCriado);
    }

    @PermitAll
    @GetMapping()
    @Override
    public ResponseEntity<List<Anuncio>> getAllResource() {
        List<Anuncio> anunciosList = StreamSupport.stream(this.anunciosServices.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(anunciosList);
    }

    @Operation(summary = "get one anuncio - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Anuncio> getResourceById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio);
    }

    @Operation(summary = "update one anuncio - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Anuncio> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateAnuncioDto resource) {
        Anuncio anuncio = this.anunciosServices.updateById(id, resource);
        return ResponseEntity.ok(anuncio);
    }

    @Operation(summary = "delete one anuncio - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.anunciosServices.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "get one imovel of anuncio associate for id - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/imoveis")
    public ResponseEntity<Imovel> getImovelOfAnuncioById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio.getImovel());
    }

    @Operation(summary = "get all categories of anuncio associate for id - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/categorias")
    public ResponseEntity<Set<Categoria>> getCategoriasOfAnuncioById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio.getCategorias());
    }
}
