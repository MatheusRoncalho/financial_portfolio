package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.TransacaoRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoResponseDTO;
import com.roncalho.financial_portfolio.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Operações de gerenciamento de transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @Operation(summary = "Criar Transação", description = "Registra uma nova transação (entrada ou saída)")
    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(@Valid @RequestBody TransacaoRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoResponseDTO response = transacaoService.criarTransacao(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Transação", description = "Obtém os detalhes de uma transação específica")
    @ApiResponse(responseCode = "200", description = "Transação encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoResponseDTO> obterTransacao(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoResponseDTO response = transacaoService.obterTransacaoPorId(id, usuarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Transação", description = "Atualiza uma transação existente")
    @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoResponseDTO> atualizarTransacao(@PathVariable Long id, @Valid @RequestBody TransacaoRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoResponseDTO response = transacaoService.atualizarTransacao(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Transação", description = "Remove uma transação existente")
    @ApiResponse(responseCode = "204", description = "Transação removida com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        transacaoService.deletarTransacao(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar Transações", description = "Lista transações com filtros opcionais por período, categoria e tipo")
    @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TransacaoResponseDTO>> listarTransacoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) String tipo) {
        Long usuarioId = obterUsuarioIdDoToken();
        List<TransacaoResponseDTO> response = transacaoService.listarTransacoes(usuarioId, inicio, fim, categoria, tipo);
        return ResponseEntity.ok(response);
    }

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}
