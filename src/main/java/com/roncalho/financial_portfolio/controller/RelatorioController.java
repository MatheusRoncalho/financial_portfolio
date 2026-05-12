package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.service.RelatorioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    private Long obterUsuarioIdDoToken() {
        // Implementar lógica para extrair o usuário ID do token JWT
        // Por enquanto, retornando um placeholder
        return 1L;
    }

    @GetMapping("/gastos-por-categoria")
    public ResponseEntity<Map<String, Object>> gastosPorCategoria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();
        Map<String, Object> response = relatorioService.gastosPorCategoria(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evolucao-mensal")
    public ResponseEntity<Map<String, Object>> evolucaoMensal(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();
        Map<String, Object> response = relatorioService.evolucaoMensal(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evolucao-anual")
    public ResponseEntity<Map<String, Object>> evolucaoAnual(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();
        Map<String, Object> response = relatorioService.evolucaoAnual(usuarioId);
        return ResponseEntity.ok(response);
    }
}

