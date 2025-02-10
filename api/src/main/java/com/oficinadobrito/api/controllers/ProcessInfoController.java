package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.entities.ProcessDataInfo;
import com.oficinadobrito.api.services.NetoNosJobsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("processamento")
public class ProcessInfoController {
    private final NetoNosJobsService tarefaService;

    public ProcessInfoController(NetoNosJobsService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(summary = "get allows a user to check the status resulting from PDF processing (barer token) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDataInfo> consultarStatus(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.consultarStatus(id));
    }
}