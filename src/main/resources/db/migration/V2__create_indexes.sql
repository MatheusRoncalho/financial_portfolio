CREATE INDEX idx_transacoes_usuario ON transacoes(usuario_id);

CREATE INDEX idx_transacoes_categoria ON transacoes(categoria_id);

CREATE INDEX idx_transacoes_data ON transacoes(data_transacao);

CREATE INDEX idx_transacoes_usuario_data ON transacoes(usuario_id, data_transacao);

CREATE INDEX idx_transacoes_usuario_categoria ON transacoes(usuario_id, categoria_id);

CREATE INDEX idx_usuarios_telegram ON usuarios(telegram_id);

CREATE INDEX idx_recorrentes_usuario ON transacoes_recorrentes(usuario_id);

CREATE INDEX idx_recorrentes_proxima_execucao ON transacoes_recorrentes(proxima_execucao);

CREATE INDEX idx_recorrentes_status_execucao ON transacoes_recorrentes(status, proxima_execucao);