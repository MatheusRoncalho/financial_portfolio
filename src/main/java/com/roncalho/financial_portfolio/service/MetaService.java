package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.dto.in.MetaRequestDTO;
import com.roncalho.financial_portfolio.dto.out.MetaResponseDTO;
import com.roncalho.financial_portfolio.exceptions.AcessoNegadoException;
import com.roncalho.financial_portfolio.exceptions.RecursoNaoEncontradoException;
import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.Meta;
import com.roncalho.financial_portfolio.model.PeriodoMeta;
import com.roncalho.financial_portfolio.model.Usuario;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import com.roncalho.financial_portfolio.repository.MetaRepository;
import com.roncalho.financial_portfolio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

        Meta meta = Meta.builder()
                .categoria(categoria)
                .usuario(usuario)
                .valorLimite(dto.valorLimite())
                .periodo(PeriodoMeta.valueOf(dto.tipoPeriodo().toUpperCase()))
                .build();

        Meta metaSalva = metaRepository.save(meta);
        return converterParaDTO(metaSalva, BigDecimal.ZERO);
    }

    public List<MetaResponseDTO> listarMetas(Long usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId).stream()
                .map(meta -> converterParaDTO(meta, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    public MetaResponseDTO obterMetaPorId(Long id, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));
        return converterParaDTO(meta, BigDecimal.ZERO);
    }

    @Transactional
    public MetaResponseDTO atualizarMeta(Long id, MetaRequestDTO dto, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));

        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(dto.categoriaId(), usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        meta.setCategoria(categoria);
        meta.setValorLimite(dto.valorLimite());
        meta.setPeriodo(PeriodoMeta.valueOf(dto.tipoPeriodo().toUpperCase()));

        Meta metaAtualizada = metaRepository.save(meta);
        return converterParaDTO(metaAtualizada, BigDecimal.ZERO);
    }

    @Transactional
    public void deletarMeta(Long id, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));
        metaRepository.deleteById(meta.getId());
    }

    public MetaResponseDTO obterMetaProgresso(Long id, Long usuarioId) {
        Meta meta = metaRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada"));

        // Implementar lógica de cálculo do valor atual baseado nas transações
        BigDecimal valorAtual = BigDecimal.ZERO; // TODO: calcular a partir do repository
        BigDecimal percentual = calcularPercentual(valorAtual, meta.getValorLimite());

        return converterParaDTO(meta, percentual);
    }

    public List<MetaResponseDTO> obterMetaProgressoTodos(Long usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId).stream()
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
                meta.getCategoria().getId(),
                meta.getCategoria().getNome(),
                meta.getValorLimite(),
                BigDecimal.ZERO, // valorAtual - será calculado
                meta.getPeriodo().toString(),
                percentual,
                meta.getCriadoEm()
        );
    }
}





