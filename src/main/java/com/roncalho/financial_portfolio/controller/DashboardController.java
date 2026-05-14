package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.out.DashboardResumoResponseDTO;
import com.roncalho.financial_portfolio.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Operações de resumo financeiro e análise")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumo")
    @Operation(summary = "Obter Resumo Financeiro", description = "Retorna um resumo consolidado do status financeiro do usuário")
    @ApiResponse(responseCode = "200", description = "Resumo obtido com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
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

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}

