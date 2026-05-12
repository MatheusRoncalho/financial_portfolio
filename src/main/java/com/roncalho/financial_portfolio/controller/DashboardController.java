package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.out.DashboardResumoResponseDTO;
import com.roncalho.financial_portfolio.service.DashboardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    private Long obterUsuarioIdDoToken() {
        // Implementar lógica para extrair o usuário ID do token JWT
        // Por enquanto, retornando um placeholder
        return 1L;
    }

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponseDTO> obterResumo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();

        if (inicio == null || fim == null) {
            DashboardResumoResponseDTO response = dashboardService.obterResumoAtual(usuarioId);
            return ResponseEntity.ok(response);
        }

        DashboardResumoResponseDTO response = dashboardService.obterResumo(inicio, fim, usuarioId);
        return ResponseEntity.ok(response);
    }
}

