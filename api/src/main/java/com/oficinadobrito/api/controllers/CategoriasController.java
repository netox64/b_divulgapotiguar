package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Categoria;
import com.oficinadobrito.api.services.CategoriasService;
import com.oficinadobrito.api.utils.dtos.categoria.CreateCategoriaDto;
import com.oficinadobrito.api.utils.dtos.categoria.UpdateCategoriaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("categorias")
public class CategoriasController implements IController<Categoria, CreateCategoriaDto, UpdateCategoriaDto> {

    private final CategoriasService categoriasServices;

    public CategoriasController(CategoriasService categoriasServices) {
        this.categoriasServices = categoriasServices;
    }

    @Operation(summary = "create one categoria")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping()
    @Override
    public ResponseEntity<Categoria> postResource(@RequestBody @Valid CreateCategoriaDto resource) {
        Categoria categoria = Categoria.createDtoToEntity(resource);
        return ResponseEntity.ok(this.categoriasServices.save(categoria));
    }

    @PermitAll
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Categoria> getResourceById(@PathVariable("id") BigInteger id) {
        Categoria categoria = this.categoriasServices.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @PermitAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Categoria>> getAllResource() {
        List<Categoria> categorias = StreamSupport.stream(this.categoriasServices.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "put one categoria")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Categoria> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateCategoriaDto resource) {

        Categoria categoriaUpdate = Categoria.updateDtoToEntity(resource);
        Categoria categoria = this.categoriasServices.updateById(id, categoriaUpdate);
        return ResponseEntity.ok(categoria);

    }

    @Operation(summary = "remove one categoria")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.categoriasServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
