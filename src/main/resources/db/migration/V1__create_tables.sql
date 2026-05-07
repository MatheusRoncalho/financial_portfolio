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
                          email VARCHAR(150) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          telegram_id BIGINT UNIQUE,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE metas (
                       id BIGSERIAL PRIMARY KEY,
                       categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                       valor_limite DECIMAL(10,2) NOT NULL CHECK (valor_limite > 0),
                       periodo VARCHAR(20) NOT NULL CHECK (periodo IN ('MENSAL', 'ANUAL')),
                       criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transacoes_recorrentes (
                            id BIGSERIAL PRIMARY KEY,
                            categoria_id BIGINT NOT NULL REFERENCES categorias(id),
                            descricao VARCHAR(255),
                            valor DECIMAL(10,2) NOT NULL CHECK (valor > 0),
                            tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
                            frequencia VARCHAR(20) NOT NULL CHECK (frequencia IN ('DIARIA', 'SEMANAL', 'MENSAL', 'ANUAL')),
                            status VARCHAR(10) NOT NULL CHECK (status IN ('ATIVO', 'CANCELADO')),
                            criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);