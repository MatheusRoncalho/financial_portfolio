package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.TransacaoFiltroRequestDTO;
import com.roncalho.financial_portfolio.dto.in.TransacaoRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoResponseDTO;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.Transacao;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.TransacaoRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import com.roncalho.financial_portfolio.specification.TransacaoSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository,
                           CategoriaRepository categoriaRepository,
                           UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        Transacao transacao = Transacao.builder()
                .descricao(dto.descricao())
                .valor(dto.valor())
                .tipo(dto.tipo())
                .dataTransacao(dto.dataTransacao() != null ? dto.dataTransacao() : null)
                .categoria(categoria)
                .usuario(usuario)
                .build();

        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return converterParaDTO(transacaoSalva);
    }

    @Transactional
    public TransacaoResponseDTO atualizarTransacao(Long id, TransacaoRequestDTO dto, Long usuarioId) {
        Transacao transacao = transacaoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada"));

        transacao.setDescricao(dto.descricao() != null ? dto.descricao() : transacao.getDescricao());
        transacao.setValor(dto.valor() != null ? dto.valor() : transacao.getValor());
        transacao.setTipo(dto.tipo() != null ? dto.tipo() : transacao.getTipo());
        transacao.setDataTransacao(dto.dataTransacao() != null ? dto.dataTransacao() : transacao.getDataTransacao());

        if (dto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));
            transacao.setCategoria(categoria);
        }

        Transacao transacaoAtualizada = transacaoRepository.save(transacao);
        return converterParaDTO(transacaoAtualizada);
    }

    public TransacaoResponseDTO obterTransacaoPorId(Long id, Long usuarioId) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada"));

        return converterParaDTO(transacao);
    }

    @Transactional
    public void deletarTransacao(Long id, Long usuarioId) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada"));

        transacaoRepository.deleteById(id);
    }

    public Page<TransacaoResponseDTO> listarTransacoes(Long usuarioId, TransacaoFiltroRequestDTO filtro, Pageable pageable) {
        return transacaoRepository.findAll(TransacaoSpecification.comFiltros(usuarioId, filtro), pageable)
                .map(this::converterParaDTO);
    }

    private TransacaoResponseDTO converterParaDTO(Transacao transacao) {
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getCategoria().getId(),
                transacao.getCategoria().getNome(),
                transacao.getDataTransacao() != null ? transacao.getDataTransacao() : null,
                transacao.getCriadoEm()
        );
    }
}
