CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          telegram_id BIGINT UNIQUE,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
                            id BIGSERIAL PRIMARY KEY,
                            usuario_id BIGINT REFERENCES usuarios(id),
                            nome VARCHAR(100) NOT NULL,
                            sistema BOOLEAN NOT NULL DEFAULT FALSE,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transacoes (
                            id BIGSERIAL PRIMARY KEY,
                            categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                            usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                            descricao VARCHAR(255),
                            valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA', 'INVESTIMENTO')),
                            data_transacao TIMESTAMP,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE metas (
                       id BIGSERIAL PRIMARY KEY,
                       usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                       categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                       valor_limite DECIMAL(10,2) NOT NULL CHECK (valor_limite > 0),
                       periodo VARCHAR(20) NOT NULL CHECK (periodo IN ('MENSAL', 'ANUAL')),
                       criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transacoes_recorrentes (
                            id BIGSERIAL PRIMARY KEY,
                            usuario_id BIGINT NOT NULL REFERENCES usuarios(id),
                            categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                            descricao VARCHAR(255),
                            valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
                            periodo VARCHAR(20) NOT NULL CHECK (periodo IN ('DIARIA', 'SEMANAL', 'MENSAL', 'ANUAL')),
                            status VARCHAR(10) NOT NULL CHECK (status IN ('ATIVO', 'CANCELADO')),
                            data_inicial TIMESTAMP NOT NULL,
                            data_final TIMESTAMP,
                            proxima_transacao TIMESTAMP NOT NULL,
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);