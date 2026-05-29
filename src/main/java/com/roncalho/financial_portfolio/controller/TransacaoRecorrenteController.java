package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteFiltroRequestDTO;
import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoRecorrenteResponseDTO;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.service.TransacaoRecorrenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/transacoes-recorrentes")
@Tag(name = "Transações Recorrentes", description = "Operações de gerenciamento de transações recorrentes")
public class TransacaoRecorrenteController {

    private final TransacaoRecorrenteService transacaoRecorrenteService;

    public TransacaoRecorrenteController(TransacaoRecorrenteService transacaoRecorrenteService) {
        this.transacaoRecorrenteService = transacaoRecorrenteService;
    }

    @PostMapping
    @Operation(summary = "Criar Transação Recorrente", description = "Cria uma transação recorrente para o usuário")
    @ApiResponse(responseCode = "201", description = "Transação recorrente criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> criarTransacaoRecorrente(@Valid @RequestBody TransacaoRecorrenteRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.criarTransacaoRecorrente(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar Transações Recorrentes", description = "Lista transações recorrentes com filtros opcionais por status e categoria")
    @ApiResponse(responseCode = "200", description = "Transações recorrentes listadas com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public Page<TransacaoRecorrenteResponseDTO> listarTransacoesRecorrentes(@Valid TransacaoRecorrenteFiltroRequestDTO filtro, Pageable pageable) {

        Long usuarioId = obterUsuarioIdDoToken();
        return transacaoRecorrenteService.listarTransacoesRecorrentes(usuarioId, filtro, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Transação Recorrente", description = "Obtém os detalhes de uma transação recorrente específica")
    @ApiResponse(responseCode = "200", description = "Transação recorrente encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação recorrente não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> obterTransacoeRecorrente(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.obterTransacaoRecorrentePorId(id, usuarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Transação Recorrente", description = "Atualiza uma transação recorrente existente")
    @ApiResponse(responseCode = "200", description = "Transação recorrente atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação recorrente não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> atualizarTransacoesRecorrentes(@PathVariable Long id, @Valid @RequestBody TransacaoRecorrenteRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.atualizarTransacaoRecorrente(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Transação Recorrente", description = "Remove uma transação recorrente existente")
    @ApiResponse(responseCode = "204", description = "Transação recorrente removida com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação recorrente não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletarTransacaoRecorrente(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        transacaoRecorrenteService.deletarTransacaoRecorrente(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar Transação Recorrente", description = "Altera o status de uma transação recorrente")
    @ApiResponse(responseCode = "200", description = "Status alterado com sucesso")
    @ApiResponse(responseCode = "400", description = "Status inválido")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Transação recorrente não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> alterarStatus(@PathVariable Long id, @RequestParam StatusRecorrencia status) {
        Long usuarioId = obterUsuarioIdDoToken();
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.alterarStatus(id, status, usuarioId);
        return ResponseEntity.ok(response);
    }

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}
