package com.roncalho.financial_portfolio.service;

import com.roncalho.financial_portfolio.model.Categoria;
import com.roncalho.financial_portfolio.model.TipoTransacao;
import com.roncalho.financial_portfolio.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria buscarOuCriar(String nome, TipoTransacao tipo) {
        return categoriaRepository.findByNome(nome.trim().toLowerCase())
                .orElseGet(() -> {
                    Categoria nova = new Categoria();
                    nova.setNome(nome.trim().toLowerCase());
                    nova.setTipo(tipo);
                    return categoriaRepository.save(nova);
                });
    }
}
