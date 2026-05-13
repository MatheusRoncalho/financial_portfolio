package com.roncalho.financial_portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "Verificação de saúde da aplicação")
public class HealthController {

    @GetMapping
    @Operation(summary = "Health Check", description = "Verifica o status de saúde da aplicação")
    @ApiResponse(responseCode = "200", description = "Aplicação está funcionando normalmente")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}

