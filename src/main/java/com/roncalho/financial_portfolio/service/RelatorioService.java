package com.roncalho.financial_portfolio.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioService {

    // TODO: Implementar lógica completa de relatórios

    public Map<String, Object> gastosPorCategoria(Long usuarioId) {
        // Implementar cálculo de gastos por categoria
        return new HashMap<>();
    }

    public Map<String, Object> evolucaoMensal(Long usuarioId) {
        // Implementar cálculo de evolução mensal
        return new HashMap<>();
    }

    public Map<String, Object> evolucaoAnual(Long usuarioId) {
        // Implementar cálculo de evolução anual
        return new HashMap<>();
    }
}

