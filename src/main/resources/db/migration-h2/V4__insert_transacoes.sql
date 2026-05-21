INSERT INTO usuarios (username, email, senha) VALUES ('Matheus', 'matheusroncalho@gmail.com', '$2a$12$yhcahElx53UH2Ugis1Q51uGSVg9wkpSOKcE95zZZ8.VBxz.lg.N2K');

INSERT INTO transacoes (
    categoria_id,
    usuario_id,
    descricao,
    valor,
    tipo,
    data_transacao
) VALUES
      (11, 1, 'Salário empresa', 4500.00, 'ENTRADA', '2026-01-05'),
      (1, 1, 'Compras no mercado', 320.50, 'SAIDA', '2026-01-06'),
      (7, 1, 'Pagamento aluguel', 1400.00, 'SAIDA', '2026-01-07'),
      (3, 1, 'Gasolina carro', 220.00, 'SAIDA', '2026-01-09'),
      (2, 1, 'Jantar restaurante', 89.90, 'SAIDA', '2026-01-10'),
      (6, 1, 'Cinema shopping', 55.00, 'SAIDA', '2026-01-12'),
      (9, 1, 'Medicamentos', 75.40, 'SAIDA', '2026-01-14'),
      (5, 1, 'Curso Java', 299.90, 'SAIDA', '2026-01-15'),
      (12, 1, 'Venda cadeira usada', 300.00, 'ENTRADA', '2026-01-18'),
      (8, 1, 'Netflix mensalidade', 39.90, 'SAIDA', '2026-01-20'),

      (11, 1, 'Salário empresa', 4500.00, 'ENTRADA', '2026-02-05'),
      (1, 1, 'Compras supermercado', 410.75, 'SAIDA', '2026-02-06'),
      (3, 1, 'Uber trabalho', 48.20, 'SAIDA', '2026-02-07'),
      (2, 1, 'Hamburgueria', 64.90, 'SAIDA', '2026-02-09'),
      (10, 1, 'Compra camiseta', 129.90, 'SAIDA', '2026-02-11'),
      (4, 1, 'Consulta médica', 180.00, 'SAIDA', '2026-02-13'),
      (12, 1, 'Freelance API', 850.00, 'ENTRADA', '2026-02-15'),
      (6, 1, 'Boliche amigos', 95.00, 'SAIDA', '2026-02-18'),
      (8, 1, 'Spotify premium', 21.90, 'SAIDA', '2026-02-20'),
      (7, 1, 'Conta energia', 210.45, 'SAIDA', '2026-02-22'),

      (11, 1, 'Salário empresa', 4500.00, 'ENTRADA', '2026-03-05'),
      (1, 1, 'Mercado atacado', 530.10, 'SAIDA', '2026-03-06'),
      (3, 1, 'Combustível', 240.00, 'SAIDA', '2026-03-08'),
      (5, 1, 'Curso Spring Boot', 399.00, 'SAIDA', '2026-03-10'),
      (12, 1, 'Venda monitor', 950.00, 'ENTRADA', '2026-03-12'),
      (2, 1, 'Pizza fim de semana', 78.50, 'SAIDA', '2026-03-13'),
      (9, 1, 'Farmácia', 62.80, 'SAIDA', '2026-03-15'),
      (10, 1, 'Tênis novo', 350.00, 'SAIDA', '2026-03-18'),
      (6, 1, 'Show música', 180.00, 'SAIDA', '2026-03-20'),
      (12, 1, 'Bônus projeto', 1200.00, 'ENTRADA', '2026-03-25');