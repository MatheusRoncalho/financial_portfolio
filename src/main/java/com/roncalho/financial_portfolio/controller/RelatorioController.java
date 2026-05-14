package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/relatorios")
@Tag(name = "Relatórios", description = "Operações de geração de relatórios financeiros")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/gastos-por-categoria")
    @Operation(summary = "Gastos por Categoria", description = "Retorna um relatório com o total de gastos agrupados por categoria")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Map<String, Object>> gastosPorCategoria(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();

        if (inicio == null) inicio = LocalDate.now().withDayOfMonth(1);
        if (fim == null) fim = LocalDate.now();

        Map<String, Object> response = relatorioService.gastosPorCategoria(usuarioId, inicio, fim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evolucao-mensal")
    @Operation(summary = "Evolução Mensal", description = "Retorna um relatório com a evolução das transações mês a mês")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Map<String, Object>> evolucaoMensal(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();

        if (inicio == null) inicio = LocalDate.now().withDayOfMonth(1).minusMonths(11);
        if (fim == null) fim = LocalDate.now();

        Map<String, Object> response = relatorioService.evolucaoMensal(usuarioId, inicio, fim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/evolucao-anual")
    @Operation(summary = "Evolução Anual", description = "Retorna um relatório com a evolução das transações ano a ano")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Map<String, Object>> evolucaoAnual(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        Long usuarioId = obterUsuarioIdDoToken();

        if (inicio == null) inicio = LocalDate.now().withDayOfYear(1).minusYears(4);
        if (fim == null) fim = LocalDate.now();

        Map<String, Object> response = relatorioService.evolucaoAnual(usuarioId, inicio, fim);
        return ResponseEntity.ok(response);
    }

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}

