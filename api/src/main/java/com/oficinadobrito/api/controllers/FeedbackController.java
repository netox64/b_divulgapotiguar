package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.entities.Feedback;
import com.oficinadobrito.api.services.FeedbacksService;
import com.oficinadobrito.api.utils.dtos.feedback.CreateFeedbackDto;
import com.oficinadobrito.api.utils.dtos.feedback.DeleteFeedbackDto;
import com.oficinadobrito.api.utils.dtos.feedback.UpdateFeedbackDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/usuarios/{id}")
    public ResponseEntity<Feedback> postFeedbackUsuario(@PathVariable("id") String id, @RequestBody @Valid CreateFeedbackDto resource) {
        Feedback novoFeedback = Feedback.createDtoToEntity(resource);
        Feedback feedbackSend = this.feedbacksService.sendFeedbackUsuario(id, novoFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackSend);
    }

    @PostMapping("/anuncios/{id}")
    public ResponseEntity<Feedback> postFeedbackAnuncio(@PathVariable("id") BigInteger id, @RequestBody @Valid CreateFeedbackDto resource) {
        Feedback novoFeedback = Feedback.createDtoToEntity(resource);
        Feedback feedbackSend = this.feedbacksService.sendFeedbackAnuncio(id, novoFeedback);
        return ResponseEntity.ok(feedbackSend);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getResourceById(@PathVariable("id") BigInteger id) {
        Feedback categoria = this.feedbacksService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Feedback>> getAllResource() {
        List<Feedback> categorias = StreamSupport.stream(this.feedbacksService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateFeedbackDto resource) {
        Feedback feedbackUpdate = Feedback.updateDtoToEntity(resource);
        Feedback feedback = this.feedbacksService.updateById(id, feedbackUpdate);
        boolean eh = resource.usuarioId().equals(resource.usuarioRequestId());

        if (!eh) {
            throw new BadRequestException("This usuario not have permission for update resource");
        }
        return ResponseEntity.ok(feedback);

    }

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
