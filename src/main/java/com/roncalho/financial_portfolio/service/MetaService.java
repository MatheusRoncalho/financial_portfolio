package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.MetaFiltroRequestDTO;
import com.roncalho.financial_portfolio.dto.in.MetaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.Meta;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.MetaRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import com.roncalho.financial_portfolio.specification.MetaSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(MetaRepository metaRepository, CategoriaRepository categoriaRepository,
                      UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public MetaResponseDTO criarMeta(MetaRequestDTO dto, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        if (metaRepository.buscarMetaConflitante(dto.categoriaId(), usuarioId, dto.dataInicio(), dto.dataFim()).isPresent()) {
            throw new RecursoNaoEncontradoException("Já existe uma meta nesta data para esta categoria");
        }

        Meta meta = Meta.builder()
                .categoria(categoria)
                .usuario(usuario)
                .valorLimite(dto.valorLimite())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .build();

        Meta metaSalva = metaRepository.save(meta);

        BigDecimal valorAtual = obterValorAtualDaMeta(metaSalva, usuarioId);
        BigDecimal porcentagem = calcularPercentual(valorAtual, metaSalva.getValorLimite());

        return converterParaDTO(metaSalva, valorAtual, porcentagem);
    }

    public Page<MetaResponseDTO> listarMetas(Long usuarioId, MetaFiltroRequestDTO filtro, Pageable pageable) {
        return metaRepository.findAll(MetaSpecification.comFiltros(usuarioId, filtro), pageable)
                .map(meta -> {
                    BigDecimal valorAtual = obterValorAtualDaMeta(meta, usuarioId);
                    BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());
                    return converterParaDTO(meta, valorAtual, percentual);
                });
    }

    public MetaResponseDTO obterMetaPorId(Long id, Long usuarioId) {
        return metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .map(meta -> {
                    BigDecimal valorAtual = obterValorAtualDaMeta(meta, usuarioId);
                    BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());
                    return converterParaDTO(meta, valorAtual, percentual);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));
    }

    @Transactional
    public MetaResponseDTO atualizarMeta(Long id, MetaRequestDTO dto, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        meta.setCategoria(categoria);
        meta.setValorLimite(dto.valorLimite());
        meta.setDataInicio(dto.dataInicio());
        meta.setDataFim(dto.dataFim());

        Meta metaAtualizada = metaRepository.save(meta);

        BigDecimal valorAtual = obterValorAtualDaMeta(meta, usuarioId);
        BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());

        return converterParaDTO(metaAtualizada, valorAtual, percentual);
    }

    @Transactional
    public void deletarMeta(Long id, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));
        metaRepository.deleteById(meta.getId());
    }

    private BigDecimal obterValorAtualDaMeta(Meta meta, Long usuarioId) {
        return metaRepository.calcularTotalGastoNoPeriodo(
                meta.getCategoria().getId(),
                usuarioId,
                meta.getDataInicio(),
                meta.getDataFim()
        );
    }

    private BigDecimal calcularPercentual(BigDecimal valorAtual, BigDecimal valorLimite) {
        if (valorLimite.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valorAtual.divide(valorLimite, 2, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    private MetaResponseDTO converterParaDTO(Meta meta,BigDecimal valorAtual, BigDecimal percentual) {
        return new MetaResponseDTO(
                meta.getId(),
                meta.getCategoria().getId(),
                meta.getCategoria().getNome(),
                meta.getValorLimite(),
                valorAtual,
                meta.getDataInicio(),
                meta.getDataFim(),
                percentual,
                meta.getCriadoEm()
        );
    }
}





