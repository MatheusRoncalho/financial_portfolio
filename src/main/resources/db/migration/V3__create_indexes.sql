CREATE INDEX idx_categorias_usuario ON categorias(usuario_id);

CREATE INDEX idx_transacoes_usuario ON transacoes(usuario_id);

CREATE INDEX idx_transacoes_categoria ON transacoes(categoria_id);

CREATE INDEX idx_transacoes_data ON transacoes(data_transacao);

CREATE INDEX idx_transacoes_usuario_data ON transacoes(usuario_id, data_transacao);

CREATE INDEX idx_transacoes_usuario_categoria ON transacoes(usuario_id, categoria_id);

CREATE INDEX idx_usuarios_telegram ON usuarios(telegram_id);

CREATE INDEX idx_recorrentes_usuario ON transacoes_recorrentes(usuario_id);

CREATE INDEX idx_recorrentes_proxima_transacao ON transacoes_recorrentes(proxima_transacao);

CREATE INDEX idx_recorrentes_status_transacao ON transacoes_recorrentes(status, proxima_transacao);

CREATE INDEX idx_metas_usuario ON metas(usuario_id);

CREATE INDEX idx_metas_usuario_categoria ON metas(usuario_id, categoria_id);

CREATE UNIQUE INDEX uq_metas_usuario_categoria_periodo ON metas(usuario_id, categoria_id, periodo);

CREATE INDEX idx_recorrencia_proxima_transacao ON transacoes_recorrentes(proxima_transacao);

CREATE INDEX idx_recorrencia_status_proxima_transacao ON transacoes_recorrentes(status, proxima_transacao);

CREATE INDEX idx_recorrencia_usuario_proxima_transacao ON transacoes_recorrentes(usuario_id, proxima_transacao);

CREATE UNIQUE INDEX uq_categoria_usuario_nome ON categorias(usuario_id, nome);