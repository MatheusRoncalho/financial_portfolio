CREATE TABLE categorias (
                            id BIGSERIAL PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transacoes (
                            id BIGSERIAL PRIMARY KEY,
                            categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                            usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                            descricao VARCHAR(255),
                            valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
                            data_transacao TIMESTAMP,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE usuarios (
                            id BIGSERIAL PRIMARY KEY,
                            nome VARCHAR(100) NOT NULL,
                            email VARCHAR(100) NOT NULL UNIQUE,
                            senha VARCHAR(100) NOT NULL,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);