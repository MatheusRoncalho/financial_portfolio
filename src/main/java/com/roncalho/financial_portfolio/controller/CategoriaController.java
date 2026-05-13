package com.roncalho.financial_portfolio.controller;

import com.roncalho.financial_portfolio.dto.in.CategoriaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.CategoriaResponseDTO;
import com.roncalho.financial_portfolio.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Operações de gerenciamento de categorias de transações")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    @Operation(summary = "Criar Categoria", description = "Cria uma nova categoria para o usuário autenticado")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        CategoriaResponseDTO response = categoriaService.criarCategoria(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar Categorias", description = "Lista todas as categorias do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        Long usuarioId = obterUsuarioIdDoToken();
        List<CategoriaResponseDTO> response = categoriaService.listarCategorias(usuarioId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter Categoria", description = "Obtém os detalhes de uma categoria específica")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<CategoriaResponseDTO> obterCategoria(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        CategoriaResponseDTO response = categoriaService.obterCategoriaPorId(id, usuarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Categoria", description = "Atualiza uma categoria existente")
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        Long usuarioId = obterUsuarioIdDoToken();
        CategoriaResponseDTO response = categoriaService.atualizarCategoria(id, dto, usuarioId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Categoria", description = "Remove uma categoria existente")
    @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        Long usuarioId = obterUsuarioIdDoToken();
        categoriaService.deletarCategoria(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    private Long obterUsuarioIdDoToken() {
        return (Long) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }
}
