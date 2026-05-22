package com.roncalho.financial_portfolio.repository;

import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRecorrenteRepository extends JpaRepository<TransacaoRecorrente, Long> {

    List<TransacaoRecorrente> findByCategoriaId(Long categoriaId);

    List<TransacaoRecorrente> findByStatus(StatusRecorrencia status);

    List<TransacaoRecorrente> findByCategoriaIdAndStatus(Long categoriaId, StatusRecorrencia status);

    Optional<TransacaoRecorrente> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<TransacaoRecorrente> findByUsuarioId(Long usuarioId);

    Page<TransacaoRecorrente> findByUsuarioId(Long usuarioId, Pageable pageable);

    List<TransacaoRecorrente> findByUsuarioIdAndStatus(Long usuarioId, StatusRecorrencia status);

    Page<TransacaoRecorrente> findByUsuarioIdAndStatus(Long usuarioId, StatusRecorrencia status, Pageable pageable);

    List<TransacaoRecorrente> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId);

    Page<TransacaoRecorrente> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId, Pageable pageable);
}

