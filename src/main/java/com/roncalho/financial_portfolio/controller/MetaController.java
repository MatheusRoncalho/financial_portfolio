package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.MetaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    public ResponseEntity<MetaResponseDTO> criar(@Valid @RequestBody MetaRequestDTO dto) {
        MetaResponseDTO response = metaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MetaResponseDTO>> listar() {
        List<MetaResponseDTO> response = metaService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaResponseDTO> obter(@PathVariable Long id) {
        MetaResponseDTO response = metaService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/progresso/{id}")
    public ResponseEntity<MetaResponseDTO> obterProgresso(@PathVariable Long id) {
        MetaResponseDTO response = metaService.obterProgresso(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/progresso")
    public ResponseEntity<List<MetaResponseDTO>> obterProgressoTodos() {
        List<MetaResponseDTO> response = metaService.obterProgressoTodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetaResponseDTO> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody MetaRequestDTO dto) {
        MetaResponseDTO response = metaService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

