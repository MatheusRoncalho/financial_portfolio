package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoRecorrenteResponseDTO;
import com.roncalho.financial_portfolio.service.TransacaoRecorrenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes-recorrentes")
public class TransacaoRecorrenteController {

    private final TransacaoRecorrenteService transacaoRecorrenteService;

    public TransacaoRecorrenteController(TransacaoRecorrenteService transacaoRecorrenteService) {
        this.transacaoRecorrenteService = transacaoRecorrenteService;
    }

    @PostMapping
    public ResponseEntity<TransacaoRecorrenteResponseDTO> criar(@Valid @RequestBody TransacaoRecorrenteRequestDTO dto) {
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoRecorrenteResponseDTO>> listar(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long categoriaId) {

        List<TransacaoRecorrenteResponseDTO> response;

        if (status != null) {
            response = transacaoRecorrenteService.listarPorStatus(status);
        } else if (categoriaId != null) {
            response = transacaoRecorrenteService.listarPorCategoria(categoriaId);
        } else {
            response = transacaoRecorrenteService.listar();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> obter(@PathVariable Long id) {
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> atualizar(@PathVariable Long id,
                                                                   @Valid @RequestBody TransacaoRecorrenteRequestDTO dto) {
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        transacaoRecorrenteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransacaoRecorrenteResponseDTO> alterarStatus(@PathVariable Long id,
                                                                       @RequestParam String status) {
        TransacaoRecorrenteResponseDTO response = transacaoRecorrenteService.alterarStatus(id, status);
        return ResponseEntity.ok(response);
    }
}

