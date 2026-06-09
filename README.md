# 🛰️ ForestFire API — Sistema de Previsão de Incêndios Florestais com Dados de Satélite

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![H2](https://img.shields.io/badge/H2-Database-lightgrey)

---

## 📌 Nome da Solução

**ForestFire API** — Plataforma de gestão de soluções espaciais para previsão e monitoramento de incêndios florestais utilizando dados de satélite.

---

## 🌍 Problema Escolhido

Os incêndios florestais representam um grande desafio ambiental, causando impactos na biodiversidade, na qualidade do ar e nos ecossistemas. A identificação e o acompanhamento dessas ocorrências são fundamentais para apoiar ações de prevenção e resposta.

Para contribuir com esse cenário, foi proposta uma API REST para cadastro, gerenciamento e acompanhamento de soluções tecnológicas relacionadas ao monitoramento ambiental. A plataforma permite organizar informações sobre iniciativas voltadas à prevenção de incêndios florestais, facilitando o controle, a consulta e o acompanhamento dessas soluções.

---

## 🎯 ODS Relacionados

| ODS | Descrição |
|-----|-----------|
| **ODS 13** | Ação Climática — combate às mudanças climáticas e seus impactos |
| **ODS 15** | Vida Terrestre — proteção e recuperação dos ecossistemas terrestres |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 21 instalado
- Maven instalado (ou usar o wrapper `./mvnw`)
- IntelliJ IDEA (recomendado) ou qualquer IDE Java

### Passo a passo

**1. Clone o repositório:**
```bash
git clone https://github.com/gustavoft2101/forestfire-api.git
cd globalsolution
```

**2. Execute a aplicação:**
```bash
./mvnw spring-boot:run
```
Ou pelo IntelliJ: abra `GlobalsolutionApplication.java` e clique no botão ▶️

**3. Acesse a API:**
```
API:         http://localhost:8080/api/solucoes
H2 Console:  http://localhost:8080/h2-console
```

**4. Configuração do H2 Console:**
```
Driver Class:  org.h2.Driver
JDBC URL:      jdbc:h2:mem:forestfire
User Name:     sa
Password:      (deixe em branco)
```

### Credenciais da API

| Usuário | Senha | Permissões |
|---------|-------|------------|
| `admin` | `admin123` | GET, POST, PUT, PATCH, DELETE |
| `user` | `user123` | Apenas GET |

---

## 🏗️ Arquitetura do Projeto

```
src/main/java/br/com/fiap/forestfire/
├── GlobalsolutionApplication.java
├── config/
│   ├── SecurityConfig.java       ← Spring Security + usuários
│   └── DataInitializer.java      ← Dados de exemplo ao iniciar
├── controller/
│   └── SolucaoEspacialController.java
├── dto/
│   ├── SolucaoRequestDTO.java
│   ├── SolucaoResponseDTO.java
│   ├── StatusUpdateDTO.java
│   └── ResumoDTO.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── SolucaoNotFoundException.java
│   ├── SolucaoCanceladaException.java
│   └── SolucaoValidadaException.java
├── model/
│   ├── SolucaoEspacial.java      ← Entidade JPA
│   └── StatusSolucao.java        ← Enum de status
├── repository/
│   └── SolucaoEspacialRepository.java
└── service/
    └── SolucaoEspacialService.java
```

---

## 🌐 Endpoints da API

| # | Método | URL | Descrição | Auth |
|---|--------|-----|-----------|------|
| 1 | `POST` | `/api/solucoes` | Cadastrar solução | ADMIN |
| 2 | `GET` | `/api/solucoes` | Listar todas | USER/ADMIN |
| 3 | `GET` | `/api/solucoes/{id}` | Buscar por ID | USER/ADMIN |
| 4 | `GET` | `/api/solucoes/area?nome=X` | Buscar por área de impacto | USER/ADMIN |
| 5 | `PUT` | `/api/solucoes/{id}` | Atualizar completo | ADMIN |
| 6 | `PATCH` | `/api/solucoes/{id}/status` | Alterar só o status | ADMIN |
| 7 | `DELETE` | `/api/solucoes/{id}` | Excluir solução | ADMIN |
| 8 | `GET` | `/api/solucoes/ods?nome=X` | Buscar por ODS | USER/ADMIN |
| 9 | `GET` | `/api/solucoes/resumo` | Resumo geral | USER/ADMIN |

---

## 📏 Regras de Negócio

- ❌ Não permite cadastro sem nome, descrição e área de impacto
- ❌ Não permite urgência ou impacto fora da escala 1–5
- ❌ Não permite alterar uma solução com status `CANCELADA`
- ❌ Não permite excluir uma solução com status `VALIDADA`
- ✅ Prioridade calculada automaticamente: `urgencia × nivelImpacto`
- ✅ Classificação automática: BAIXA (1–10), MÉDIA (11–16), ALTA (17–25)
- ✅ Retorna `404` quando solução não encontrada
- ✅ Retorna `400` quando dados enviados são inválidos
- ✅ Retorna `409` quando regra de negócio é violada

---

## 👨‍💻 Integrantes do Grupo

| Nome | RM |
|------|----|
| Gabriel Machado Belardino | 550121 |
| Matheus Aparecido Rocha Plati | 559813 |
| Gustavo Pandolfo Meroni | 560271 |
| Gustavo Neri Santos | 560239 |
| Gustavo Franco Tárano | 559616 |

---

## 🎥 Vídeo Explicativo

> Link do vídeo: https://youtu.be/I2Yp8jCFLwQ

---

## 🧪 Testando com Postman

Importe o arquivo `ForestFire_API.postman_collection.json` incluído no repositório.

Configure em todas as requisições:
```
Authorization → Basic Auth
Username: admin
Password: admin123
```

---

## 🗄️ Banco de Dados

O projeto utiliza **H2 in-memory** — o banco é criado automaticamente ao iniciar a aplicação e populado com 5 soluções de exemplo. Não é necessário nenhum script SQL adicional.

Para visualizar os dados acesse: `http://localhost:8080/h2-console`
