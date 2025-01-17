package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.services.ImoveisService;
import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("imoveis")
public class ImoveisController implements IController<Imovel, CreateImovelDto, UpdateImovelDto> {

    private final ImoveisService imoveisService;

    public ImoveisController(ImoveisService imoveisService) {
        this.imoveisService = imoveisService;
    }

    @PostMapping()
    @Override
    public ResponseEntity<Imovel> postResource(@RequestBody @Valid CreateImovelDto resource) {
        Imovel novo = Imovel.createDtoToEntity(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.imoveisService.save(novo));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Imovel> getResourceById(@PathVariable("id") BigInteger id) {
        Imovel imovel = this.imoveisService.findById(id);
        return ResponseEntity.ok(imovel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Imovel>> getAllResource() {
        List<Imovel> imoveis = StreamSupport.stream(this.imoveisService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(imoveis);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Imovel> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateImovelDto resource) {
        Imovel novoUpdate = Imovel.updateDtoToEntity(resource);
        Imovel imovel = this.imoveisService.updateById(id, novoUpdate);
        return ResponseEntity.ok(imovel);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.imoveisService.delete(id);
        return ResponseEntity.ok().build();
    }
}
