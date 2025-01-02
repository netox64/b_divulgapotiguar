package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.services.AnunciosService;
import com.oficinadobrito.api.services.CategoriasService;
import com.oficinadobrito.api.services.ImoveisService;
import com.oficinadobrito.api.utils.dtos.anuncio.CreateAnuncioDto;
import com.oficinadobrito.api.utils.dtos.anuncio.UpdateAnuncioDto;
import jakarta.annotation.security.PermitAll;
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
@RequestMapping("anuncios")
// @SecurityRequirement(name = "JWT")
public class AnunciosController implements IController<Anuncio, CreateAnuncioDto, UpdateAnuncioDto> {

    private final AnunciosService anunciosServices;
    private final ImoveisService imoveisService;
    private final CategoriasService categoriasService;

    public AnunciosController(AnunciosService anunciosServices, ImoveisService imoveisService, CategoriasService categoriasService) {
        this.anunciosServices = anunciosServices;
        this.imoveisService = imoveisService;
        this.categoriasService = categoriasService;
    }

    @PermitAll
    @PostMapping()
    @Override
    public ResponseEntity<?> postResource(@RequestBody @Valid CreateAnuncioDto resource) {
        Anuncio novo = Anuncio.createDtoToEntity(resource);
        Imovel imovel = this.imoveisService.findById(resource.imovelId());
        novo.setImovel(imovel);
        if (resource.categoriasIds() != null) {
            novo.setCategorias(this.categoriasService.getAllCategoriaWithIds(resource.categoriasIds()));
        }
        var anuncioCriado = this.anunciosServices.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(anuncioCriado);
    }

    @PermitAll
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Anuncio>> getAllResource() {
        List<Anuncio> anunciosList = StreamSupport.stream(this.anunciosServices.findAll().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok(anunciosList);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getResourceById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateAnuncioDto resource) {
        Anuncio anuncioUpdate = Anuncio.updateDtoToEntity(resource);

        // verify Imóvel
        Imovel imovel = this.imoveisService.findById(resource.imovelId());
        anuncioUpdate.setImovel(imovel);

        // verify Categorias
        if (resource.categoriasIds() != null) {
            anuncioUpdate.setCategorias(this.categoriasService.getAllCategoriaWithIds(resource.categoriasIds()));
        }

        // Atualizar anúncio
        Anuncio anuncio = this.anunciosServices.updateById(id, anuncioUpdate);
        return ResponseEntity.ok(anuncio);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.anunciosServices.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/imoveis")
    public ResponseEntity<?> getImovelOfAnuncioById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio.getImovel());
    }

    @GetMapping("/{id}/categorias")
    public ResponseEntity<?> getCategoriasOfAnuncioById(@PathVariable("id") BigInteger id) {
        Anuncio anuncio = this.anunciosServices.findById(id);
        return ResponseEntity.ok(anuncio.getCategorias());
    }
}
