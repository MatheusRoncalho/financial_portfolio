package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoRecorrenteResponseDTO;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.TransacaoRecorrenteRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoRecorrenteService {

    private final TransacaoRecorrenteRepository transacaoRecorrenteRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoRecorrenteService(TransacaoRecorrenteRepository transacaoRecorrenteRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.transacaoRecorrenteRepository = transacaoRecorrenteRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TransacaoRecorrenteResponseDTO criarTransacaoRecorrente(TransacaoRecorrenteRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        TransacaoRecorrente transacaoRecorrente = TransacaoRecorrente.builder()
                .descricao(dto.descricao())
                .valor(dto.valor())
                .tipo(dto.tipo())
                .periodo(dto.tipoPeriodo())
                .status(StatusRecorrencia.ATIVO)
                .categoria(categoria)
                .usuario(usuario)
                .dataInicial(dto.dataInicial())
                .dataFinal(dto.dataFinal() != null ? dto.dataFinal() : null)
                .build();

        transacaoRecorrente.setProximaTransacao(calcularProximaTransacao(transacaoRecorrente));

        TransacaoRecorrente transacaoRecorrenteSalva = transacaoRecorrenteRepository.save(transacaoRecorrente);
        return converterParaDTO(transacaoRecorrenteSalva);
    }

    public List<TransacaoRecorrenteResponseDTO> listarTransacoesRecorrentes(Long usuarioId) {
        return transacaoRecorrenteRepository.findByUsuarioId(usuarioId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public TransacaoRecorrenteResponseDTO obterTransacaoRecorrentePorId(Long id, Long usuarioId) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação recorrente não encontrada"));
        return converterParaDTO(transacao);
    }

    @Transactional
    public TransacaoRecorrenteResponseDTO atualizarTransacaoRecorrente(Long id, TransacaoRecorrenteRequestDTO dto, Long usuarioId) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação recorrente não encontrada"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        transacao.setDescricao(dto.descricao());
        transacao.setValor(dto.valor());
        transacao.setTipo(dto.tipo());
        transacao.setPeriodo(dto.tipoPeriodo());
        transacao.setCategoria(categoria);
        transacao.setDataFinal(dto.dataFinal() != null ? dto.dataFinal() : null);

        TransacaoRecorrente TransacaoRecorrenteAtualizada = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(TransacaoRecorrenteAtualizada);
    }

    @Transactional
    public void deletarTransacaoRecorrente(Long id, Long usuarioId) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação recorrente não encontrada"));
        transacaoRecorrenteRepository.deleteById(transacao.getId());
    }

    //TODO: verificar lógica
    @Transactional
    public TransacaoRecorrenteResponseDTO alterarStatus(Long id, String novoStatus, Long usuarioId) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação recorrente não encontrada"));

        StatusRecorrencia status = StatusRecorrencia.valueOf(novoStatus.toUpperCase());
        transacao.setStatus(status);

        TransacaoRecorrente transacaoRecorrenteAtualizada = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(transacaoRecorrenteAtualizada);
    }

    public List<TransacaoRecorrenteResponseDTO> listarTransacoesRecorrentesPorStatus(String status, Long usuarioId) {
        StatusRecorrencia statusEnum = StatusRecorrencia.valueOf(status.toUpperCase());
        return transacaoRecorrenteRepository.findByUsuarioIdAndStatus(usuarioId, statusEnum).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<TransacaoRecorrenteResponseDTO> listarTransacoesRecorrentesPorCategoria(Long categoriaId, Long usuarioId) {
        return transacaoRecorrenteRepository.findByUsuarioIdAndCategoriaId(usuarioId, categoriaId).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private TransacaoRecorrenteResponseDTO converterParaDTO(TransacaoRecorrente transacao) {
        return new TransacaoRecorrenteResponseDTO(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getCategoria().getId(),
                transacao.getCategoria().getNome(),
                transacao.getPeriodo(),
                transacao.getStatus(),
                transacao.getDataInicial(),
                transacao.getDataFinal(),
                transacao.getProximaTransacao(),
                transacao.getCriadoEm()
        );
    }

    //TODO: Verificar lógica da próxima transação
    private LocalDateTime calcularProximaTransacao(TransacaoRecorrente transacaoRecorrente) {
        if (transacaoRecorrente.getStatus() != StatusRecorrencia.ATIVO) {
            return null;
        }

        LocalDateTime inicio = transacaoRecorrente.getDataInicial();
        switch (transacaoRecorrente.getPeriodo()) {
            case DIARIA:
                return inicio.plusDays(1);
            case SEMANAL:
                return inicio.plusWeeks(1);
            case MENSAL:
                return inicio.plusMonths(1);
            case ANUAL:
                return inicio.plusYears(1);
            default:
                return null;
        }
    }
}



