-- Categorias
CREATE INDEX idx_categorias_usuario ON categorias(usuario_id);
CREATE UNIQUE INDEX uq_categorias_usuario_nome ON categorias(usuario_id, nome);

-- Transações
CREATE INDEX idx_transacoes_usuario ON transacoes(usuario_id);
CREATE INDEX idx_transacoes_categoria ON transacoes(categoria_id);
CREATE INDEX idx_transacoes_data ON transacoes(data_transacao);
CREATE INDEX idx_transacoes_usuario_data ON transacoes(usuario_id, data_transacao);
CREATE INDEX idx_transacoes_usuario_categoria ON transacoes(usuario_id, categoria_id);
CREATE INDEX idx_transacoes_tipo ON transacoes(tipo);
CREATE INDEX idx_transacoes_usuario_categoria_data ON transacoes(usuario_id, categoria_id, data_transacao);

-- Metas
CREATE INDEX idx_metas_usuario ON metas(usuario_id);
CREATE INDEX idx_metas_categoria ON metas(categoria_id);
CREATE INDEX idx_metas_data_fim ON metas(data_fim);
CREATE INDEX idx_metas_usuario_data_fim ON metas(usuario_id, data_fim);

-- Transações Recorrentes
CREATE INDEX idx_recorrentes_usuario ON transacoes_recorrentes(usuario_id);
CREATE INDEX idx_recorrentes_categoria ON transacoes_recorrentes(categoria_id);
CREATE INDEX idx_recorrentes_usuario_categoria ON transacoes_recorrentes(usuario_id, categoria_id);
CREATE INDEX idx_recorrentes_status ON transacoes_recorrentes(status);