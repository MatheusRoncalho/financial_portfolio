package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.CategoriaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.CategoriaResponseDTO;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO dto, Long usuarioId) {
        String nomeCategoria = dto.nome().trim().substring(0, 1).toUpperCase()
                + dto.nome().trim().substring(1).toLowerCase();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (categoriaRepository.findByNomeAndUsuarioId(nomeCategoria, usuarioId).isPresent()) {
            throw new IllegalArgumentException("Categoria com este nome já existe");
        }

        Categoria categoria = Categoria.builder()
                .nome(nomeCategoria)
                .usuario(usuario)
                .sistema(false)
                .build();

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return converterParaDTO(categoriaSalva);
    }

    public List<CategoriaResponseDTO> listarCategorias(Long usuarioId) {

        return categoriaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO obterCategoriaPorId(Long id, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada ou acesso negado"));
        return converterParaDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO dto, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada ou acesso negado"));

        if (categoria.getSistema()) {
            throw new IllegalArgumentException("Não é permitido atualizar categorias do sistema");
        }

        if (!categoria.getNome().equals(dto.nome().toLowerCase()) &&
            categoriaRepository.findByNomeAndUsuarioId(dto.nome().toLowerCase(), usuarioId).isPresent()) {
            throw new IllegalArgumentException("Categoria com este nome já existe");
        }

        categoria.setNome(dto.nome().toLowerCase());
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return converterParaDTO(categoriaAtualizada);
    }

    @Transactional
    public void deletarCategoria(Long id, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada ou acesso negado"));

        if (categoria.getSistema()) {
            throw new IllegalArgumentException("Não é permitido deletar categorias do sistema");
        }
        categoriaRepository.deleteById(categoria.getId());
    }

    private CategoriaResponseDTO converterParaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getSistema(),
                categoria.getCriadoEm()
        );
    }
}
