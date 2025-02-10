package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.entities.Feedback;
import com.oficinadobrito.api.services.FeedbacksService;
import com.oficinadobrito.api.utils.dtos.feedback.CreateFeedbackDto;
import com.oficinadobrito.api.utils.dtos.feedback.DeleteFeedbackDto;
import com.oficinadobrito.api.utils.dtos.feedback.UpdateFeedbackDto;
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
@RequestMapping("feedbacks")
public class FeedbackController {

    private final FeedbacksService feedbacksService;

    public FeedbackController(FeedbacksService feedbacksService) {
        this.feedbacksService = feedbacksService;
    }

    @Operation(summary = "post one feedback to usuario - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/usuarios/{id}")
    public ResponseEntity<Feedback> postFeedbackUsuario(@PathVariable("id") String id, @RequestBody @Valid CreateFeedbackDto resource) {
        Feedback novoFeedback = Feedback.createDtoToEntity(resource);
        Feedback feedbackSend = this.feedbacksService.sendFeedbackUsuario(id, novoFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackSend);
    }

    @Operation(summary = "post one feedback to ANuncio - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/anuncios/{id}")
    public ResponseEntity<Feedback> postFeedbackAnuncio(@PathVariable("id") BigInteger id, @RequestBody @Valid CreateFeedbackDto resource) {
        Feedback novoFeedback = Feedback.createDtoToEntity(resource);
        Feedback feedbackSend = this.feedbacksService.sendFeedbackAnuncio(id, novoFeedback);
        return ResponseEntity.ok(feedbackSend);

    }

    @Operation(summary = "get one feedback - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getResourceById(@PathVariable("id") BigInteger id) {
        Feedback categoria = this.feedbacksService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @Operation(summary = "get  all feedbacks  - (barer jwt) -> role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Feedback>> getAllResource() {
        List<Feedback> categorias = StreamSupport.stream(this.feedbacksService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(categorias);
    }

    @Operation(summary = "update one feedback - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateFeedbackDto resource) {
        Feedback feedback = this.feedbacksService.updateById(id, resource);
        boolean eh = resource.usuarioId().equals(resource.usuarioRequestId());

        if (!eh) {
            throw new BadRequestException("This usuario not have permission for update resource");
        }
        return ResponseEntity.ok(feedback);

    }

    @Operation(summary = "delete one feedback to usuario - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id, @RequestBody @Valid DeleteFeedbackDto resource) {

        Feedback feedback = this.feedbacksService.findById(id);

        boolean isOwner = feedback.getUsuario().getUsuarioId().equals(resource.usuarioRequestId());
        if (!isOwner) {
            throw new BadRequestException("This usuario not have permission for update resource");
        }

       this.feedbacksService.delete(id);
        return ResponseEntity.ok().build();

    }
}
