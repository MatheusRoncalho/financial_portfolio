ALTER TABLE transacoes DROP CONSTRAINT transacoes_tipo_check;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_tipo_check
    CHECK (tipo IN ('ENTRADA', 'SAIDA', 'INVESTIMENTO'));