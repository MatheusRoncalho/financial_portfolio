package com.roncalho.financial_portfolio.service;

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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // TODO: Listar com paginação e filtros (Specification Pattern)
    public List<TransacaoResponseDTO> listarTransacoes(Long usuarioId, LocalDate inicio, LocalDate fim, Long categoriaId, String tipo) {

        List<Transacao> transacoes;

        if (inicio != null && fim != null && categoriaId != null && tipo != null) {
            LocalDateTime inicioDatetime = inicio.atStartOfDay();
            LocalDateTime fimDatetime = fim.atTime(LocalTime.MAX);
            TipoTransacao tipoEnum = TipoTransacao.valueOf(tipo.toUpperCase());
            transacoes = transacaoRepository.findByUsuarioIdAndDataTransacaoBetween(usuarioId, inicioDatetime, fimDatetime)
                    .stream()
                    .filter(t -> t.getCategoria().getId().equals(categoriaId) && t.getTipo().equals(tipoEnum))
                    .collect(Collectors.toList());
        } else if (inicio != null && fim != null) {
            LocalDateTime inicioDatetime = inicio.atStartOfDay();
            LocalDateTime fimDatetime = fim.atTime(LocalTime.MAX);
            transacoes = transacaoRepository.findByUsuarioIdAndDataTransacaoBetween(usuarioId, inicioDatetime, fimDatetime);
        } else if (categoriaId != null) {
            transacoes = transacaoRepository.findByUsuarioIdAndCategoriaId(usuarioId, categoriaId);
        } else if (tipo != null) {
            TipoTransacao tipoEnum = TipoTransacao.valueOf(tipo.toUpperCase());
            transacoes = transacaoRepository.findByUsuarioIdAndTipo(usuarioId, tipoEnum);
        } else {
            transacoes = transacaoRepository.findByUsuarioId(usuarioId);
        }

        return transacoes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
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
