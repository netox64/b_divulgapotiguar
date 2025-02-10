package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.entities.Pagamento;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.PagamentosService;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.pagamento.CreatePagamentoDto;
import com.oficinadobrito.api.utils.dtos.usuario.DataQrDto;
import com.oficinadobrito.api.utils.dtos.usuario.SvgDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("pagamentos")
public class PagamentosController {

    private final PagamentosService pagamentosService;
    private final UsuariosService usuariosService;

    public PagamentosController(PagamentosService pagamentosService, UsuariosService usuariosService) {
        this.pagamentosService = pagamentosService;
        this.usuariosService = usuariosService;
    }
    @Operation(summary = "create one payment -  (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<Pagamento> postResource(@RequestBody @Valid CreatePagamentoDto resource) {
        Pagamento novo = Pagamento.createDtoToEntity(resource);
        Usuario usuario = this.usuariosService.findUsuarioById(resource.usuarioId());
        novo.setUsuario(usuario);
        var pagamento = this.pagamentosService.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @Operation(summary = "get all payment -  (barer jwt) -> role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pagamento>> getAllResource() {
        List<Pagamento> pagamentoList = StreamSupport.stream(this.pagamentosService.findAll().spliterator(), false)
                .toList();
        return ResponseEntity.ok(pagamentoList);
    }

    @Operation(summary = "get one payment -  (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getResourceById(@PathVariable("id") BigInteger id) {
        Pagamento pagamento = this.pagamentosService.findById(id);
        return ResponseEntity.ok(pagamento);
    }

    @Operation(summary = "delete one payment -  (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.pagamentosService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "post value and create one qrcode - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/qrcode")
    public ResponseEntity<SvgDto> getQrcode(@RequestBody @Valid DataQrDto data) {
        String qrcode = this.pagamentosService.pixGenerateQRCodeAutoSvg(data.username(), data.cpf(),
                data.valorOriginal());
        return ResponseEntity.ok(new SvgDto(qrcode));
    }

    @Operation(summary = "post value and create one qrcode - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/mockqrcode")
    public ResponseEntity<SvgDto> getMockQrcode(@RequestBody @Valid DataQrDto data) {
        return ResponseEntity.ok(new SvgDto(
                "https://genqrcode.com/embedded?style=0&inner_eye_style=0&outer_eye_style=0&logo=null&color=%23000000FF&background_color=%23FFFFFFFF&inner_eye_color=%23000000&outer_eye_color=%23000000&imageformat=svg&language=en&frame_style=0&frame_text=SCAN%20ME&frame_color=%23000ert_colors=false&gradient_style=0&gradient_color_start=%23FF0000&gradient_color_end=%237F007F&gradient_start_offset=5&gradient_end_offset=95&stl_type=1&logo_remove_background=null&stl_size=100&stl_qr_height=1.5&stl_base_height=2&stl_include_stands=false&stl_qr_magnet_type=3&stl_qr_magnet_count=0&type=0&text=https%3A%2F%2Fgithub.com%2000&invFnetox64&width=500&height=500&bordersize=2"));
    }

}
