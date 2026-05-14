# 📊 API Financial Portfolio - Resumo de Endpoints

## 🔹 1. AUTH / AUTENTICAÇÃO
`POST /auth/register`
- **Função**: Criar usuário
- **Body**: { "username": "", "email": "", "password": "" }

`POST /auth/login`
- **Função**: Autenticar usuário e retornar JWT
- **Body**: { "email": "", "senha": "" }
- **Response**: { "token": "", "tipo": "Bearer" }

`POST /auth/logout`
- **Função**: Invalidar sessão/token

`GET /auth/me`
- **Função**: Retornar dados do usuário logado

---

## 🔹 2. TRANSAÇÕES
`POST /transacoes`
- **Função**: Registrar gasto ou ganho
- **Body**: { "descricao": "", "valor": 0, "tipo": "ENTRADA|SAIDA", "dataTransacao": "", "categoriaId": 0 }

`DELETE /transacoes/{id}`
- **Função**: Excluir transação

`PUT /transacoes/{id}`
- **Função**: Atualizar transação
- **Body**: { "descricao": "", "valor": 0, "tipo": "ENTRADA|SAIDA", "dataTransacao": "", "categoriaId": 0 }

`GET /transacoes/{id}`
- **Função**: Buscar transação específica

`GET /transacoes`
- **Query Params**:
  - `inicio=2026-05-01`
  - `fim=2026-05-31`
  - `page=0`
  - `size=20`
  - `categoria=`
  - `tipo=ENTRADA|SAIDA`

---

## 🔹 3. CATEGORIAS
`POST /categorias`
- **Função**: Criar categoria
- **Body**: { "nome": "" }

`GET /categorias`
- **Função**: Listar categorias

`PUT /categorias/{id}`
- **Função**: Atualizar categoria
- **Body**: { "nome": "" }

`DELETE /categorias/{id}`
- **Função**: Excluir categoria

---

## 🔹 4. METAS
`POST /metas`
- **Função**: Criar meta
- **Body**: { "id": 1, "valorLimite": 1000.00, "tipoPeriodo": "MENSAL|ANUAL" }

`GET /metas`
- **Função**: Listar metas

`GET /metas/{id}`
- **Função**: Buscar meta específica

`GET /metas/progresso/{id}`
- **Função**: Buscar progresso de uma meta específica
- **Response**: { "id": 1, "categoria": "", "valorLimite": 1000, "valorAtual": 500, "tipoPeriodo": "MENSAL", "percentualAtualizado": 50 }

`GET /metas/progresso`
- **Função**: Buscar progresso de todas as metas

`PUT /metas/{id}`
- **Função**: Atualizar meta
- **Body**: { "id": 1, "valorLimite": 1000.00, "tipoPeriodo": "MENSAL|ANUAL" }

`DELETE /metas/{id}`
- **Função**: Excluir meta

---

## 🔹 5. TRANSAÇÕES RECORRENTES
`POST /transacoes-recorrentes`
- **Função**: Criar recorrência
- **Body**: { "descricao": "", "valor": 0, "tipo": "ENTRADA|SAIDA", "categoriaId": 0, "tipoPeriodo": "DIARIA|SEMANAL|MENSAL|ANUAL" }

`GET /transacoes-recorrentes`
- **Query Params**:
  - `status=ATIVO|CANCELADO`
  - `categoriaId=1`
- **Função**: Listar recorrências com filtros opcionais

`GET /transacoes-recorrentes/{id}`
- **Função**: Buscar recorrência específica

`PUT /transacoes-recorrentes/{id}`
- **Função**: Atualizar recorrência
- **Body**: { "descricao": "", "valor": 0, "tipo": "ENTRADA|SAIDA", "categoriaId": 0, "tipoPeriodo": "DIARIA|SEMANAL|MENSAL|ANUAL" }

`DELETE /transacoes-recorrentes/{id}`
- **Função**: Excluir recorrência

`PATCH /transacoes-recorrentes/{id}/status`
- **Função**: Ativar/desativar recorrência
- **Query Params**: `status=ATIVO|CANCELADO`

---

## 🔹 6. DASHBOARD / RESUMO FINANCEIRO
`GET /dashboard/resumo`
- **Query Params**:
  - `inicio=2026-05-01` (opcional)
  - `fim=2026-05-31` (opcional)
- **Response**: { "saldoAtual": 5000, "totalGanhos": 8000, "totalGastos": 3000, "saldoPeriodo": 5200 }

---

## 🔹 7. RELATÓRIOS
`GET /relatorios/gastos-por-categoria`
- **Query Params**: `inicio=`, `fim=`
- **Função**: Gastos por categoria

`GET /relatorios/evolucao-mensal`
- **Query Params**: `inicio=`, `fim=`
- **Função**: Evolução mensal

`GET /relatorios/evolucao-anual`
- **Query Params**: `inicio=`, `fim=`
- **Função**: Evolução anual

---

## 🔹 8. HEALTH CHECK
`GET /health`
- **Função**: Verificar status da API
- **Response**: { "status": "UP", "timestamp": "2026-05-08T10:30:00" }

---

## 📝 Autenticação
Todos os endpoints (exceto `/auth/register`, `/auth/login` e `/health`) requerem:
```
Header: Authorization: Bearer <JWT_TOKEN>
```

## ✅ Implementações Importantes
- ✅ Todos os controllers criados
- ✅ Todos os services criados
- ✅ JWT Authentication
- ✅ Validações de entrada (DTOs)
- ✅ Tratamento de erros básico
- ✅ Health Check endpoint

## ⚠️ TODO - Próximas implementações
- [ ] Implementar filtros de listagem de transações com data e categoria
- [ ] Implementar cálculo de progresso de metas
- [ ] Implementar cálculo de dashboard
- [ ] Implementar relatórios
- [ ] Adicionar validações mais robustas
- [ ] Adicionar tratamento de exceções global
- [ ] Adicionar logs
- [ ] Adicionar testes unitários
- [ ] Implementar cache
- [ ] Adicionar paginação nos endpoints de listagem

