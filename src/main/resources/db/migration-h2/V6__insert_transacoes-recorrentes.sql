INSERT INTO transacoes_recorrentes (usuario_id, categoria_id, descricao, valor, tipo, periodo, status, data_inicial, data_final, criado_em) VALUES

-- === SAÍDAS MENSAIS (Despesas Fixas) ===
(1, 7, 'Aluguel apartamento', 1500.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-01', NULL, CURRENT_TIMESTAMP),
(1, 8, 'Netflix assinatura', 39.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-10', NULL, CURRENT_TIMESTAMP),
(1, 8, 'Spotify premium', 21.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 3, 'Combustível carro', 300.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-05', NULL, CURRENT_TIMESTAMP),
(1, 4, 'Plano saúde', 450.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-20', NULL, CURRENT_TIMESTAMP),

-- === SAÍDAS SEMANAIS ===
(1, 1, 'Compras supermercado', 250.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-06', NULL, CURRENT_TIMESTAMP),
(1, 2, 'Restaurante sexta', 120.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-10', '2026-06-30', CURRENT_TIMESTAMP),

-- === SAÍDAS DIÁRIAS ===
(1, 2, 'Café e lanche dia', 15.00, 'SAIDA', 'DIARIA', 'ATIVO', '2026-01-02', NULL, CURRENT_TIMESTAMP),
(1, 3, 'Uber para trabalho', 25.00, 'SAIDA', 'DIARIA', 'ATIVO', '2026-01-02', NULL, CURRENT_TIMESTAMP),

-- === ENTRADAS RECORRENTES ===
(1, 11, 'Salário empresa', 5000.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-01-05', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Freelance projects', 800.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Consultoria semanal', 500.00, 'ENTRADA', 'SEMANAL', 'ATIVO', '2026-01-20', NULL, CURRENT_TIMESTAMP),

-- === INVESTIMENTOS ===
(1, 12, 'Investimento fundo imobiliário', 1000.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-10', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Aplicação renda fixa', 500.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Compra ações B3', 2000.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-20', '2026-12-31', CURRENT_TIMESTAMP),

-- === SAÍDAS ANUAIS ===
(1, 9, 'Medicamentos anuais', 800.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-02-15', NULL, CURRENT_TIMESTAMP),
(1, 10, 'Guarda-roupa renovação', 1200.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-03-01', NULL, CURRENT_TIMESTAMP),
(1, 5, 'Cursos online anual', 600.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-04-10', NULL, CURRENT_TIMESTAMP),

-- === TRANSAÇÕES CANCELADAS ===
(1, 3, 'Academia personal trainer', 400.00, 'SAIDA', 'MENSAL', 'CANCELADO', '2025-06-01', '2025-12-31', CURRENT_TIMESTAMP),
(1, 6, 'Viagem mensal (cancelada)', 500.00, 'SAIDA', 'MENSAL', 'CANCELADO', '2025-01-01', '2025-06-30', CURRENT_TIMESTAMP),
(1, 2, 'Delivery comida (parou)', 100.00, 'SAIDA', 'SEMANAL', 'CANCELADO', '2025-01-01', '2025-08-31', CURRENT_TIMESTAMP),

-- === MAIS SAÍDAS MENSAIS ===
(1, 6, 'Cinema e lazer', 150.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-25', NULL, CURRENT_TIMESTAMP),
(1, 5, 'Plataforma educação', 199.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-12', NULL, CURRENT_TIMESTAMP),
(1, 1, 'Verduras e frutas', 180.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-08', NULL, CURRENT_TIMESTAMP),
(1, 4, 'Farmácia medicamentos', 100.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-18', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Bônus semestral empresa', 3000.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-06-15', NULL, CURRENT_TIMESTAMP);INSERT INTO transacoes_recorrentes (usuario_id, categoria_id, descricao, valor, tipo, periodo, status, data_inicial, data_final, criado_em) VALUES

-- === SAÍDAS MENSAIS (Despesas Fixas) ===
(1, 7, 'Aluguel apartamento', 1500.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-01', NULL, CURRENT_TIMESTAMP),
(1, 8, 'Netflix assinatura', 39.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-10', NULL, CURRENT_TIMESTAMP),
(1, 8, 'Spotify premium', 21.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 3, 'Combustível carro', 300.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-05', NULL, CURRENT_TIMESTAMP),
(1, 4, 'Plano saúde', 450.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-20', NULL, CURRENT_TIMESTAMP),

-- === SAÍDAS SEMANAIS ===
(1, 1, 'Compras supermercado', 250.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-06', NULL, CURRENT_TIMESTAMP),
(1, 2, 'Restaurante sexta', 120.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-10', '2026-06-30', CURRENT_TIMESTAMP),

-- === SAÍDAS DIÁRIAS ===
(1, 2, 'Café e lanche dia', 15.00, 'SAIDA', 'DIARIA', 'ATIVO', '2026-01-02', NULL, CURRENT_TIMESTAMP),
(1, 3, 'Uber para trabalho', 25.00, 'SAIDA', 'DIARIA', 'ATIVO', '2026-01-02', NULL, CURRENT_TIMESTAMP),

-- === ENTRADAS RECORRENTES ===
(1, 11, 'Salário empresa', 5000.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-01-05', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Freelance projects', 800.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Consultoria semanal', 500.00, 'ENTRADA', 'SEMANAL', 'ATIVO', '2026-01-20', NULL, CURRENT_TIMESTAMP),

-- === INVESTIMENTOS ===
(1, 12, 'Investimento fundo imobiliário', 1000.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-10', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Aplicação renda fixa', 500.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-15', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Compra ações B3', 2000.00, 'INVESTIMENTO', 'MENSAL', 'ATIVO', '2026-01-20', '2026-12-31', CURRENT_TIMESTAMP),

-- === SAÍDAS ANUAIS ===
(1, 9, 'Medicamentos anuais', 800.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-02-15', NULL, CURRENT_TIMESTAMP),
(1, 10, 'Guarda-roupa renovação', 1200.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-03-01', NULL, CURRENT_TIMESTAMP),
(1, 5, 'Cursos online anual', 600.00, 'SAIDA', 'ANUAL', 'ATIVO', '2026-04-10', NULL, CURRENT_TIMESTAMP),

-- === TRANSAÇÕES CANCELADAS ===
(1, 3, 'Academia personal trainer', 400.00, 'SAIDA', 'MENSAL', 'CANCELADO', '2025-06-01', '2025-12-31', CURRENT_TIMESTAMP),
(1, 6, 'Viagem mensal (cancelada)', 500.00, 'SAIDA', 'MENSAL', 'CANCELADO', '2025-01-01', '2025-06-30', CURRENT_TIMESTAMP),
(1, 2, 'Delivery comida (parou)', 100.00, 'SAIDA', 'SEMANAL', 'CANCELADO', '2025-01-01', '2025-08-31', CURRENT_TIMESTAMP),

-- === MAIS SAÍDAS MENSAIS ===
(1, 6, 'Cinema e lazer', 150.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-25', NULL, CURRENT_TIMESTAMP),
(1, 5, 'Plataforma educação', 199.90, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-12', NULL, CURRENT_TIMESTAMP),
(1, 1, 'Verduras e frutas', 180.00, 'SAIDA', 'SEMANAL', 'ATIVO', '2026-01-08', NULL, CURRENT_TIMESTAMP),
(1, 4, 'Farmácia medicamentos', 100.00, 'SAIDA', 'MENSAL', 'ATIVO', '2026-01-18', NULL, CURRENT_TIMESTAMP),
(1, 12, 'Bônus semestral empresa', 3000.00, 'ENTRADA', 'MENSAL', 'ATIVO', '2026-06-15', NULL, CURRENT_TIMESTAMP);