package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteFiltroRequestDTO;
import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteRequestDTO;
import com.roncalho.financial_portfolio.dto.out.TransacaoRecorrenteResponseDTO;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.TransacaoRecorrenteRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import com.roncalho.financial_portfolio.specification.TransacaoRecorrenteSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


        TransacaoRecorrente transacaoRecorrenteSalva = transacaoRecorrenteRepository.save(transacaoRecorrente);
        return converterParaDTO(transacaoRecorrenteSalva);
    }

    public Page<TransacaoRecorrenteResponseDTO> listarTransacoesRecorrentes(Long usuarioId, TransacaoRecorrenteFiltroRequestDTO filtro, Pageable pageable) {
        return transacaoRecorrenteRepository.findAll(TransacaoRecorrenteSpecification.comFiltros(usuarioId, filtro), pageable)
                .map(this::converterParaDTO);
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

    @Transactional
    public TransacaoRecorrenteResponseDTO alterarStatus(Long id, StatusRecorrencia novoStatus, Long usuarioId) {
        TransacaoRecorrente transacao = transacaoRecorrenteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transação recorrente não encontrada"));

        transacao.setStatus(novoStatus);

        TransacaoRecorrente transacaoRecorrenteAtualizada = transacaoRecorrenteRepository.save(transacao);
        return converterParaDTO(transacaoRecorrenteAtualizada);
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
                transacao.getCriadoEm()
        );
    }
}



