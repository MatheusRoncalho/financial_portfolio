CREATE TABLE categorias (
                            id BIGSERIAL PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transacoes (
                            id BIGSERIAL PRIMARY KEY,
                            descricao VARCHAR(255),
                            valor DECIMAL(10,2) NOT NULL,
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
                            categoria_id BIGINT NOT NULL,
                            usuario_id BIGINT,
                            data_transacao TIMESTAMP NOT NULL,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_categoria
                                FOREIGN KEY (categoria_id)
                                REFERENCES categorias(id)

);