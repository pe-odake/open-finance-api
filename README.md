# Open Finance Dashboard — API

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?logo=flyway&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?logo=swagger&logoColor=black)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

API REST que alimenta o Open Finance Dashboard, simulando o ecossistema de Open Finance brasileiro. Gerencia usuários, contas bancárias fictícias, transações categorizadas e consentimentos de acesso, com sincronização automática periódica e geração assíncrona de extratos.
O backend é o núcleo do Open Finance Dashboard. Ele implementa os domínios financeiros centrais do padrão Open Finance do Banco Central do Brasil em escala simulada: autenticação segura com JWT, modelo de consentimento por escopo, sincronização periódica de dados de contas (mock) e processamento assíncrono de relatórios.

---

## Stack

| Tecnologia | Uso |
|---|---|
| **Java 21** | Linguagem principal |
| **Spring Boot 3.4.5** | Framework base |
| **Spring Security** | Autenticação JWT e autorização |
| **Spring Data JPA** | Persistência com Hibernate |
| **PostgreSQL** | Banco de dados relacional |
| **Flyway** | Migrações e versionamento do schema |
| **JJWT / Auth0 JWT** | Geração e validação de tokens JWT |
| **SpringDoc OpenAPI 2.8** | Documentação Swagger UI |
| **Lombok** | Redução de boilerplate |

---

## Funcionalidades

### ✅ Implementadas

#### Autenticação e segurança
- Registro de usuários com senha armazenada em BCrypt
- Login com e-mail e senha retornando JWT (2h de expiração)
- Spring Security configurando rotas protegidas por autenticação
- Filtro JWT que valida token em todas as requisições protegidas

#### Contas bancárias
- CRUD completo de contas fictícias
- Tipos suportados: corrente, poupança, cartão de crédito
- Bancos suportados: Nubank, Itaú, Bradesco, Banco do Brasil, Santander, Inter, C6Bank, Caixa

#### Transações
- Criação e listagem de transações vinculadas a uma conta
- Listagem filtrada por conta
- Categorização manual: Alimentação, Transporte, Lazer, Saúde, Educação, Moradia, Renda, Mercado, Assinatura
- Tipos: receita e despesa

#### Infraestrutura
- Arquitetura hexagonal com Ports & Adapters
- Separação Domain Model / Entity com mappers entre camadas
- Tratamento centralizado de erros com `GlobalExceptionHandler`
- Documentação Swagger UI via SpringDoc OpenAPI
- Migrações de banco versionadas com Flyway
- Perfis de configuração: `dev` e `prod`

### 🚧 Em desenvolvimento

#### Refresh token
- Renovação automática do JWT antes de expirar
- Endpoint dedicado para refresh

#### Paginação e filtros de transações
- Paginação server-side
- Filtros por categoria, período e busca por descrição

#### Vinculação de contas por usuário
- FK de contas para o usuário autenticado
- Listagem de contas filtrada por usuário logado

#### Sincronização de dados
- Sincronização manual por conta
- Sincronização automática periódica via Spring Scheduler
- Geração programática de transações simulando movimentações bancárias

#### Estatísticas
- Resumo mensal: total de entradas, saídas e saldo
- Gastos por categoria com valor e percentual
- Evolução patrimonial consolidada

#### Consentimentos
- Modelo de consentimento por escopo (saldo, transações, fatura)
- Criação, listagem e revogação de consentimentos
- Expiração configurável

#### Extratos assíncronos
- Geração de extrato (PDF/CSV) em background via Spring Async
- Endpoint de polling para status e download

#### Notificações em tempo real
- Endpoint SSE com conexão persistente
- Eventos ao concluir sincronização e geração de extrato

#### Cache
- Spring Cache com Redis para saldo consolidado
- Invalidação automática ao concluir sincronização

---

## Arquitetura

O projeto adota **arquitetura hexagonal (Ports & Adapters)** com separação em camadas:

```
adapter/       → entrada (controllers, DTOs) e saída (repositories, entities, mappers)
application/   → ports (interfaces) + core (domain models, services)
config/        → configurações do Spring (Security, JWT)
exception/     → tratamento centralizado de erros (handler, tipos, DTOs)
```

**Fluxo de uma requisição:**
```
HTTP Request
    → Controller (adapter/in)
        → Port Interface (application/port/in)
            → Service (application/core/service)
                → Repository Port (application/port/out)
                    → Repository Impl (adapter/out)
                        → JPA Repository → PostgreSQL
        ← Retorno mapeado para DTO de resposta
    ← HTTP Response
```

---

## Estrutura de pastas

```
open-finance-api/
├── src/
│   ├── main/
│   │   ├── java/com/pedroodake/openfinanceapi/
│   │   │   │
│   │   │   ├── adapter/
│   │   │   │   ├── in/
│   │   │   │   │   └── controller/
│   │   │   │   │       ├── ContaController.java          # Endpoints de contas
│   │   │   │   │       ├── TransacaoController.java      # Endpoints de transações
│   │   │   │   │       ├── UsuarioController.java        # Registro, login e listagem
│   │   │   │   │       ├── request/
│   │   │   │   │       │   ├── conta/
│   │   │   │   │       │   ├── transacao/
│   │   │   │   │       │   └── usuario/
│   │   │   │   │       └── response/
│   │   │   │   │           ├── conta/
│   │   │   │   │           ├── transacao/
│   │   │   │   │           └── usuario/
│   │   │   │   │
│   │   │   │   └── out/
│   │   │   │       └── repository/
│   │   │   │           ├── ContaRepositoryImpl.java       # Impl do port de contas
│   │   │   │           ├── TransacaoRepositoryImpl.java   # Impl do port de transações
│   │   │   │           ├── UsuarioRepositoryImpl.java     # Impl do port de usuários
│   │   │   │           ├── entity/
│   │   │   │           ├── mapper/
│   │   │   │           └── persistence/
│   │   │   │
│   │   │   ├── application/
│   │   │   │   ├── core/
│   │   │   │   │   ├── domain/
│   │   │   │   │   │   ├── model/
│   │   │   │   │   │   │   ├── Conta.java
│   │   │   │   │   │   │   ├── Transacao.java
│   │   │   │   │   │   │   └── Usuario.java
│   │   │   │   │   │   └── enums/
│   │   │   │   │   │       ├── Banco.java             # NUBANK, ITAU, BRADESCO...
│   │   │   │   │   │       ├── Categoria.java         # ALIMENTACAO, TRANSPORTE, LAZER...
│   │   │   │   │   │       ├── Perfil.java            # ADMIN, USER
│   │   │   │   │   │       ├── TipoConta.java         # CORRENTE, POUPANCA, CARTAO_CREDITO
│   │   │   │   │   │       └── TipoTransacao.java     # RECEITA, DESPESA
│   │   │   │   │   └── service/
│   │   │   │   │       ├── AutenticacaoService.java   # UserDetailsService do Spring Security
│   │   │   │   │       ├── ContaService.java          # Lógica de negócio de contas
│   │   │   │   │       ├── LoginService.java          # Autenticação e geração de JWT
│   │   │   │   │       ├── TransacaoService.java      # Lógica de negócio de transações
│   │   │   │   │       └── UsuarioService.java        # Registro e gestão de usuários
│   │   │   │   └── port/
│   │   │   │       ├── in/
│   │   │   │       │   ├── AtualizacaoController.java       # Port de atualização
│   │   │   │       │   ├── CadastroController.java          # Port de cadastro
│   │   │   │       │   ├── DetalhamentoController.java      # Port de detalhamento
│   │   │   │       │   ├── ExclusaoController.java          # Port de exclusão
│   │   │   │       │   ├── ListagemController.java          # Port de listagem
│   │   │   │       │   ├── ListagemFiltradaController.java  # Port de listagem filtrada
│   │   │   │       │   └── LoginPort.java                   # Port de login
│   │   │   │       └── out/
│   │   │   │           ├── ContaRepository.java             # Port de persistência de contas
│   │   │   │           ├── TransacaoRepository.java         # Port de persistência de transações
│   │   │   │           └── UsuarioRepository.java           # Port de persistência de usuários
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── security/
│   │   │   │       ├── SecurityConfigurations.java    # Configuração do Spring Security
│   │   │   │       ├── dto/
│   │   │   │       ├── filter/
│   │   │   │       └── service/
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── dto/
│   │   │   │   ├── handler/
│   │   │   │   │   └── GlobalExceptionHandler.java    # Tratamento centralizado
│   │   │   │   └── type/
│   │   │   │       ├── conta/
│   │   │   │       ├── transacao/
│   │   │   │       └── usuario/
│   │   │   │
│   │   │   └── OpenFinanceApiApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-prod.properties
│   │       └── db/migration/
│   │           ├── V1__criacao-tabela-usuario.sql
│   │           ├── V2__criacao-tabela-contas.sql
│   │           └── V3__criacao-tabela-transacao.sql
│   │
│   └── test/
│       └── java/com/pedroodake/openfinanceapi/
│           ├── OpenFinanceApiApplicationTests.java
│           └── util/
│               └── Encriptador.java
│
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

---

## Como rodar localmente

**Pré-requisitos:** Java 21, Maven 3.9+, PostgreSQL

```bash
# Clonar o repositório
git clone https://github.com/pe-odake/open-finance-api.git
cd open-finance-api

# Configurar variáveis de ambiente
# Editar .env com DB_USER e DB_PASSWORD do PostgreSQL

# Rodar a aplicação
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

A API estará disponível em `http://localhost:8080`
Documentação Swagger UI: `http://localhost:8080/swagger-ui.html`

### Variáveis de ambiente

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_USER` | Usuário do PostgreSQL | `postgres` |
| `DB_PASSWORD` | Senha do PostgreSQL | `admin123` |

---

## Endpoints

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | `/usuarios` | Cadastro de novo usuário | Público |
| POST | `/usuarios/login` | Login — retorna JWT | Público |
| GET | `/usuarios` | Lista todos os usuários | JWT |
| GET | `/usuarios/{id}` | Detalhes de um usuário | JWT |
| GET | `/contas` | Lista todas as contas | JWT |
| POST | `/contas` | Adiciona nova conta | JWT |
| GET | `/contas/{id}` | Detalhes de uma conta | JWT |
| PUT | `/contas/{id}` | Atualiza uma conta | JWT |
| DELETE | `/contas/{id}` | Remove uma conta | JWT |
| GET | `/transacoes` | Lista todas as transações | JWT |
| POST | `/transacoes` | Cria uma nova transação | JWT |
| GET | `/transacoes/{id}` | Detalhes de uma transação | JWT |
| GET | `/transacoes/conta/{id}` | Transações de uma conta específica | JWT |

---

## Roadmap

- [x] Autenticação JWT (registro e login)
- [x] CRUD de contas bancárias
- [x] CRUD de transações com categorização manual
- [x] Arquitetura hexagonal com Ports & Adapters
- [x] Tratamento centralizado de erros
- [x] Documentação Swagger UI
- [ ] Vinculação de contas ao usuário autenticado (FK)
- [ ] Refresh token
- [ ] Paginação e filtros em transações
- [ ] Categorização automática (Strategy pattern)
- [ ] Estatísticas e resumo financeiro
- [ ] Consentimentos por escopo
- [ ] Sincronização automática (Spring Scheduler)
- [ ] Extratos assíncronos (Spring Async)
- [ ] Notificações em tempo real (SSE)
- [ ] Cache com Redis
- [ ] Docker e Docker Compose
- [ ] Testes unitários e de integração

---

## Autor

**Pedro Odake**
[GitHub](https://github.com/pe-odake) · [LinkedIn](https://linkedin.com/in/pedro-odake/) · [pedroodake.com](https://pedroodake.com)
