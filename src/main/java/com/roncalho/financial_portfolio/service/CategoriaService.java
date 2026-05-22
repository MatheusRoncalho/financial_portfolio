package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.CategoriaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.CategoriaResponseDTO;
import com.roncalho.financial_portfolio.exceptions.AcessoNegadoException;
import com.roncalho.financial_portfolio.exceptions.EntidadeJaExisteException;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        if (categoriaRepository.findByNomeAndUsuarioId(nomeCategoria, usuarioId).isPresent()) {
            throw new EntidadeJaExisteException("Categoria com este nome já existe");
        }

        Categoria categoria = Categoria.builder()
                .nome(nomeCategoria)
                .usuario(usuario)
                .sistema(false)
                .build();

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return converterParaDTO(categoriaSalva);
    }

    public Page<CategoriaResponseDTO> listarCategorias(Long usuarioId, Pageable pageable) {
        return categoriaRepository.findByUsuarioIdOrSistemaIsTrue(usuarioId, pageable).map(this::converterParaDTO);
    }

    public CategoriaResponseDTO obterCategoriaPorId(Long id, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
        return converterParaDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO dto, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        String nomeCategoria = dto.nome().trim().substring(0, 1).toUpperCase()
                + dto.nome().trim().substring(1).toLowerCase();

        if (categoria.getSistema()) {
            throw new AcessoNegadoException("Não é permitido atualizar categorias criadas pelo sistema");
        }

        if (categoriaRepository.findByNomeAndUsuarioId(nomeCategoria, usuarioId).isPresent()) {
            throw new EntidadeJaExisteException("Categoria com este nome já existe");
        }

        categoria.setNome(nomeCategoria);
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return converterParaDTO(categoriaAtualizada);
    }

    @Transactional
    public void deletarCategoria(Long id, Long usuarioId) {
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        if (categoria.getSistema()) {
            throw new AcessoNegadoException("Não é permitido deletar categorias do sistema");
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
