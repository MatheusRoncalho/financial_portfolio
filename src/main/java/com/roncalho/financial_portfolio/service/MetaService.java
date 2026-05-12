package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.MetaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.Meta;
import com.roncalho.financial_portfolio.model.PeriodoMeta;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.MetaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final CategoriaRepository categoriaRepository;

    public MetaService(MetaRepository metaRepository, CategoriaRepository categoriaRepository) {
        this.metaRepository = metaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public MetaResponseDTO criar(MetaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        Meta meta = Meta.builder()
                .categoria(categoria)
                .valorLimite(dto.valorLimite())
                .periodo(PeriodoMeta.valueOf(dto.tipoPeriodo().toUpperCase()))
                .build();

        Meta saved = metaRepository.save(meta);
        return converterParaDTO(saved, BigDecimal.ZERO);
    }

    public List<MetaResponseDTO> listar() {
        return metaRepository.findAll().stream()
                .map(meta -> converterParaDTO(meta, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    public MetaResponseDTO obterPorId(Long id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));
        return converterParaDTO(meta, BigDecimal.ZERO);
    }

    @Transactional
    public MetaResponseDTO atualizar(Long id, MetaRequestDTO dto) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));

        Categoria categoria = categoriaRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        meta.setCategoria(categoria);
        meta.setValorLimite(dto.valorLimite());
        meta.setPeriodo(PeriodoMeta.valueOf(dto.tipoPeriodo().toUpperCase()));

        Meta updated = metaRepository.save(meta);
        return converterParaDTO(updated, BigDecimal.ZERO);
    }

    @Transactional
    public void deletar(Long id) {
        if (!metaRepository.existsById(id)) {
            throw new IllegalArgumentException("Meta não encontrada");
        }
        metaRepository.deleteById(id);
    }

    public MetaResponseDTO obterProgresso(Long id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));

        // Implementar lógica de cálculo do valor atual baseado nas transações
        BigDecimal valorAtual = BigDecimal.ZERO; // TODO: calcular a partir do repository
        BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());

        return converterParaDTO(meta, percentual);
    }

    public List<MetaResponseDTO> obterProgressoTodos() {
        return metaRepository.findAll().stream()
                .map(meta -> {
                    BigDecimal valorAtual = BigDecimal.ZERO; // TODO: calcular a partir do repository
                    BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());
                    return converterParaDTO(meta, percentual);
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calcularPercentual(BigDecimal valorAtual, BigDecimal valorLimite) {
        if (valorLimite.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return valorAtual.divide(valorLimite, 2, java.math.RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    private MetaResponseDTO converterParaDTO(Meta meta, BigDecimal percentual) {
        return new MetaResponseDTO(
                meta.getId(),
                meta.getCategoria().getNome(),
                meta.getValorLimite(),
                BigDecimal.ZERO, // valorAtual - será calculado
                meta.getPeriodo().toString(),
                percentual
        );
    }
}

