package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoRecorrenteResponseDTO;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.FrequenciaRecorrencia;
import com.roncalho.financial_portfolio.model.StatusRecorrencia;
import com.roncalho.financial_portfolio.model.TipoTransacao;
import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.TransacaoRecorrenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoRecorrenteService {

    private final TransacaoRecorrenteRepository transacaoRecorrenteRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoRecorrenteService(TransacaoRecorrenteRepository transacaoRecorrenteRepository,
                                     CategoriaRepository categoriaRepository) {
        this.transacaoRecorrenteRepository = transacaoRecorrenteRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public TransacaoRecorrenteResponseDTO criar(TransacaoRecorrenteRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        TransacaoRecorrente transacao = TransacaoRecorrente.builder()
                .descricao(dto.descricao())
                .valor(dto.valor())
                .tipo(TipoTransacao.valueOf(dto.tipo().toUpperCase()))
                .frequencia(FrequenciaRecorrencia.valueOf(dto.tipoPeriodo().toUpperCase()))
                .status(StatusRecorrencia.ATIVO)
                .categoria(categoria)
                .build();

        TransacaoRecorrente saved = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(saved);
    }

    public List<TransacaoRecorrenteResponseDTO> listar() {
        return transacaoRecorrenteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public TransacaoRecorrenteResponseDTO obterPorId(Long id) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação recorrente não encontrada"));
        return converterParaDTO(transacao);
    }

    @Transactional
    public TransacaoRecorrenteResponseDTO atualizar(Long id, TransacaoRecorrenteRequestDTO dto) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação recorrente não encontrada"));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(TipoTransacao.valueOf(dto.tipo().toUpperCase()));
        transacao.setFrequencia(FrequenciaRecorrencia.valueOf(dto.tipoPeriodo().toUpperCase()));
        transacao.setCategoria(categoria);

        TransacaoRecorrente updated = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(updated);
    }

    @Transactional
    public void deletar(Long id) {
        if (!transacaoRecorrenteRepository.existsById(id)) {
            throw new IllegalArgumentException("Transação recorrente não encontrada");
        }
        transacaoRecorrenteRepository.deleteById(id);
    }

    @Transactional
    public TransacaoRecorrenteResponseDTO alterarStatus(Long id, String novoStatus) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação recorrente não encontrada"));

        StatusRecorrencia status = StatusRecorrencia.valueOf(novoStatus.toUpperCase());
        transacao.setStatus(status);

        TransacaoRecorrente updated = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(updated);
    }

    public List<TransacaoRecorrenteResponseDTO> listarPorStatus(String status) {
        StatusRecorrencia statusEnum = StatusRecorrencia.valueOf(status.toUpperCase());
        return transacaoRecorrenteRepository.findByStatus(statusEnum).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<TransacaoRecorrenteResponseDTO> listarPorCategoria(Long categoriaId) {
        return transacaoRecorrenteRepository.findByCategoriaId(categoriaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private TransacaoRecorrenteResponseDTO converterParaDTO(TransacaoRecorrente transacao) {
        LocalDateTime proximaTransacao = calcularProximaTransacao(transacao);

        return new TransacaoRecorrenteResponseDTO(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getStatus().toString(),
                proximaTransacao != null ? proximaTransacao.toString() : null
        );
    }

    private LocalDateTime calcularProximaTransacao(TransacaoRecorrente transacao) {
        if (!transacao.getStatus().equals(StatusRecorrencia.ATIVO)) {
            return null;
        }

        LocalDateTime agora = LocalDateTime.now();
        switch (transacao.getFrequencia()) {
            case DIARIA:
                return agora.plusDays(1);
            case SEMANAL:
                return agora.plusWeeks(1);
            case MENSAL:
                return agora.plusMonths(1);
            case ANUAL:
                return agora.plusYears(1);
            default:
                return null;
        }
    }
}

