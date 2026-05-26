package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.MetaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.service.MetaService;
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
@RequestMapping("/metas")
@Tag(name = "Metas", description = "Operações de gerenciamento de metas financeiras")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @PostMapping
    @Operation(summary = "Criar Meta", description = "Cria uma nova meta financeira para o usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Meta criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MetaResponseDTO> criarMeta(@Valid @RequestBody MetaRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        MetaResponseDTO response = metaService.criarMeta(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar Metas", description = "Lista todas as metas do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Metas listadas com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public Page<MetaResponseDTO> listarMetas(Pageable pageable) {
        Long usuarioId = obterUsuarioIdDoToken();
        return metaService.listarMetas(usuarioId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Meta", description = "Obtém os detalhes de uma meta específica")
    @ApiResponse(responseCode = "200", description = "Meta encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MetaResponseDTO> obterMeta(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        MetaResponseDTO response = metaService.obterMetaPorId(id, usuarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Meta", description = "Atualiza uma meta existente")
    @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<MetaResponseDTO> atualizarMeta(@PathVariable Long id, @Valid @RequestBody MetaRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        MetaResponseDTO response = metaService.atualizarMeta(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Meta", description = "Remove uma meta existente")
    @ApiResponse(responseCode = "204", description = "Meta removida com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Meta não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletarMeta(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        metaService.deletarMeta(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}

