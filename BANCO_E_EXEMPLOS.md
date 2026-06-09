# 🗄️ Instruções de Banco de Dados

## Tecnologia utilizada
O projeto utiliza **H2 Database in-memory**.
O banco é criado e populado **automaticamente** ao iniciar a aplicação.
Não é necessário instalar nenhum banco de dados externo.

---

## Acessar o H2 Console (interface visual do banco)

1. Inicie a aplicação
2. Acesse: http://localhost:8080/h2-console
3. Configure a conexão:

```
Driver Class:  org.h2.Driver
JDBC URL:      jdbc:h2:mem:globalsolution
User Name:     sa
Password:      (deixe em branco)
```

4. Clique em **Connect**

---

## Estrutura da tabela gerada automaticamente

```sql
CREATE TABLE TB_SOLUCAO_ESPACIAL (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(100)  NOT NULL,
    descricao        VARCHAR(500)  NOT NULL,
    area_impacto     VARCHAR(100)  NOT NULL,
    status           VARCHAR(50)   NOT NULL,
    urgencia         INTEGER       NOT NULL,
    nivel_impacto    INTEGER       NOT NULL,
    prioridade       INTEGER       NOT NULL,
    ods_relacionado  VARCHAR(100)  NOT NULL,
    fonte_dados      VARCHAR(200),
    regiao_monitorada VARCHAR(200),
    data_cadastro    TIMESTAMP     NOT NULL,
    data_atualizacao TIMESTAMP
);
```

---

## Dados carregados automaticamente (DataInitializer)

Ao iniciar, 5 soluções são inseridas automaticamente:

| ID | Nome | Área | Status | Prioridade |
|----|------|------|--------|------------|
| 1 | SentinelFire - Detecção por Satélite | Amazônia | EM_OPERACAO | 25 (ALTA) |
| 2 | CerradoWatch - Monitoramento Térmico | Cerrado | VALIDADA | 16 (MÉDIA) |
| 3 | PantanalAlert - Prevenção de Queimadas | Pantanal | EM_DESENVOLVIMENTO | 20 (ALTA) |
| 4 | AtlânticaGuard - Proteção da Mata Atlântica | Mata Atlântica | SUSPENSA | 9 (BAIXA) |
| 5 | CaatingaMonitor - Semiárido | Caatinga | EM_DESENVOLVIMENTO | 6 (BAIXA) |

---

## Consultas SQL úteis no H2 Console

```sql
-- Ver todas as soluções
SELECT * FROM TB_SOLUCAO_ESPACIAL;

-- Ver soluções por status
SELECT * FROM TB_SOLUCAO_ESPACIAL WHERE STATUS = 'EM_DESENVOLVIMENTO';

-- Ver soluções com alta prioridade
SELECT * FROM TB_SOLUCAO_ESPACIAL WHERE PRIORIDADE >= 17;

-- Contar por área de impacto
SELECT AREA_IMPACTO, COUNT(*) AS TOTAL
FROM TB_SOLUCAO_ESPACIAL
GROUP BY AREA_IMPACTO;

-- Ver soluções por ODS
SELECT * FROM TB_SOLUCAO_ESPACIAL
WHERE UPPER(ODS_RELACIONADO) LIKE UPPER('%ODS 15%');
```

---

# 📋 Exemplos de JSON — Requisições e Respostas

---

## POST /api/solucoes — Cadastrar

### Requisição:
```json
{
    "nome": "FireSat - Monitoramento em Tempo Real",
    "descricao": "Sistema de monitoramento em tempo real de focos de incêndio na Amazônia utilizando constelação de nanossatélites com sensores infravermelhos.",
    "areaImpacto": "Amazônia",
    "status": "EM_DESENVOLVIMENTO",
    "urgencia": 5,
    "nivelImpacto": 5,
    "odsRelacionado": "ODS 15 - Vida Terrestre",
    "fonteDados": "Constelação NanoSat / INPE",
    "regiaoMonitorada": "Amazonas - AM"
}
```

### Resposta (201 Created):
```json
{
    "id": 6,
    "nome": "FireSat - Monitoramento em Tempo Real",
    "descricao": "Sistema de monitoramento em tempo real de focos de incêndio na Amazônia utilizando constelação de nanossatélites com sensores infravermelhos.",
    "areaImpacto": "Amazônia",
    "status": "EM_DESENVOLVIMENTO",
    "urgencia": 5,
    "nivelImpacto": 5,
    "prioridade": 25,
    "classificacaoPrioridade": "ALTA",
    "odsRelacionado": "ODS 15 - Vida Terrestre",
    "fonteDados": "Constelação NanoSat / INPE",
    "regiaoMonitorada": "Amazonas - AM",
    "dataCadastro": "2025-06-08T10:00:00",
    "dataAtualizacao": "2025-06-08T10:00:00"
}
```

---

## GET /api/solucoes/resumo — Resumo Geral

### Resposta (200 OK):
```json
{
    "totalSolucoes": 5,
    "quantidadePorStatus": {
        "EM_OPERACAO": 1,
        "VALIDADA": 1,
        "EM_DESENVOLVIMENTO": 2,
        "SUSPENSA": 1
    },
    "quantidadePorAreaImpacto": {
        "Amazônia": 1,
        "Cerrado": 1,
        "Pantanal": 1,
        "Mata Atlântica": 1,
        "Caatinga": 1
    },
    "solucoesAltaPrioridade": [
        {
            "id": 1,
            "nome": "SentinelFire - Detecção por Satélite",
            "prioridade": 25,
            "classificacaoPrioridade": "ALTA"
        },
        {
            "id": 3,
            "nome": "PantanalAlert - Prevenção de Queimadas",
            "prioridade": 20,
            "classificacaoPrioridade": "ALTA"
        }
    ]
}
```

---

## PATCH /api/solucoes/{id}/status — Alterar Status

### Requisição:
```json
{
    "status": "VALIDADA"
}
```

### Valores válidos para status:
```
EM_DESENVOLVIMENTO
VALIDADA
EM_OPERACAO
SUSPENSA
CANCELADA
```

---

## Erros — Exemplos de Respostas

### 404 Not Found:
```json
{
    "timestamp": "2025-06-08T10:00:00",
    "status": 404,
    "erro": "Not Found",
    "mensagem": "Solução não encontrada com o ID: 999"
}
```

### 400 Bad Request (validação):
```json
{
    "timestamp": "2025-06-08T10:00:00",
    "status": 400,
    "erro": "Dados inválidos",
    "campos": {
        "nome": "Nome deve ter entre 3 e 100 caracteres",
        "descricao": "Descrição deve ter entre 10 e 500 caracteres",
        "urgencia": "Urgência máxima é 5"
    }
}
```

### 409 Conflict (regra de negócio):
```json
{
    "timestamp": "2025-06-08T10:00:00",
    "status": 409,
    "erro": "Conflict",
    "mensagem": "Não é permitido alterar uma solução com status CANCELADA."
}
```

### 403 Forbidden (sem permissão):
```json
{
    "timestamp": "2025-06-08T10:00:00",
    "status": 403,
    "erro": "Forbidden",
    "mensagem": "Acesso negado"
}
```
