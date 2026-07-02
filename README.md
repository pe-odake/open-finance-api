# Open Finance Dashboard — API

API REST que alimenta o Open Finance Dashboard, simulando o ecossistema de Open Finance brasileiro. Gerencia usuários, contas bancárias fictícias, transações categorizadas e consentimentos de acesso, com sincronização automática periódica e geração assíncrona de extratos.

---

## Para que serve

O backend é o núcleo do Open Finance Dashboard. Ele implementa os domínios financeiros centrais do padrão Open Finance do Banco Central do Brasil em escala simulada: autenticação segura com JWT, modelo de consentimento por escopo, sincronização periódica de dados de contas (mock) e processamento assíncrono de relatórios.

A arquitetura segue o padrão MVC com separação em camadas bem definidas — **adapters** para entrada e saída de dados, **application** para a lógica de negócio, **config** para configurações do framework e **exception** para tratamento centralizado de erros — demonstrando maturidade técnica aplicável a projetos reais de fintech.

---

## Funcionalidades

### Autenticação e segurança
- Registro e login de usuários com senha armazenada em BCrypt
- Geração de JWT de acesso (30 min) e refresh token (7 dias)
- Endpoint de renovação de token (`/auth/refresh`)
- Spring Security configurando rotas protegidas por autenticação
- Autorização por escopo nos consentimentos (leitura de saldo, leitura de transações, leitura de fatura)

### Contas bancárias
- CRUD completo de contas fictícias por usuário
- Tipos suportados: corrente, poupança, cartão de crédito
- Endpoint de sincronização manual por conta
- Sincronização automática periódica via Spring Scheduler (a cada 30 minutos)
- Dados gerados programaticamente simulando movimentações bancárias reais

### Transações
- Listagem paginada com filtros combinados: conta, categoria e período
- Busca por descrição com debounce no frontend
- Categorização automática via padrão Strategy — cada regra analisa a descrição e atribui uma categoria
- Edição manual de categoria por transação
- Cálculo de totais de entradas e saídas por período

### Estatísticas
- Resumo mensal: total de entradas, saídas e saldo
- Gastos por categoria com valor e percentual
- Evolução patrimonial consolidada dos últimos N meses
- Insight automático comparando o mês atual com o anterior

### Consentimentos
- Criação automática de consentimento ao adicionar uma conta (escopo por permissão)
- Listagem de consentimentos ativos e histórico de revogados
- Revogação com registro de data e motivo
- Expiração configurável (padrão: 90 dias)

### Extratos assíncronos
- Solicitação de geração de extrato (PDF ou CSV) por conta e período
- Processamento em background via Spring Async — retorna imediatamente com ID do job
- Endpoint de polling para verificar status (pendente, processando, concluído, erro)
- Download do arquivo gerado

### Notificações em tempo real
- Endpoint SSE (`/notifications/stream`) com conexão persistente com o frontend
- Eventos publicados ao concluir sincronização automática (sucesso ou erro por conta)
- Eventos publicados ao concluir geração de extrato
- Spring Async garante que o processo de sincronização não bloqueia a thread principal

### Cache
- Spring Cache com Redis para saldo consolidado por usuário
- Invalidação automática ao concluir sincronização
- TTL configurável por tipo de dado (padrão: 5 minutos para saldos)

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.4.5 | Framework base |
| Spring Security | Gerenciado | Autenticação JWT e autorização |
| Spring Data JPA | Gerenciado | Persistência com Hibernate |
| Spring Data Redis | Gerenciado | Cache de saldo consolidado |
| Spring Cache | Gerenciado | Abstração de cache com `@Cacheable` |
| Spring Web | Gerenciado | API REST e Server-Sent Events |
| Spring Scheduler | Gerenciado | Jobs periódicos de sincronização |
| Spring Async | Gerenciado | Processamento assíncrono de extratos |
| PostgreSQL | Gerenciado | Banco de dados relacional principal |
| Redis | Gerenciado | Cache em memória |
| Flyway | Gerenciado | Migrações e versionamento do schema |
| JJWT | 0.12.5 | Geração e validação de tokens JWT |
| SpringDoc OpenAPI | 2.5.0 | Documentação Swagger UI |
| Lombok | Gerenciado | Redução de boilerplate |
| Testcontainers | Gerenciado | Testes de integração com banco real |
| JUnit 5 + Mockito | Gerenciado | Testes unitários e de integração |
| Docker / Docker Compose | — | Ambiente local com PostgreSQL e Redis |

---

## Arquitetura

O projeto adota MVC com organização em quatro camadas principais:

```
adapters/     → entrada (controllers, DTOs) e saída (repositórios)
application/  → lógica de negócio (services, domain models, strategies)
config/       → configurações do Spring (Security, Cache, Async, OpenAPI)
exception/    → tratamento centralizado de erros
```

**Fluxo de uma requisição:**
```
HTTP Request
    → Controller (adapter/in)
        → Service (application)
            → Repository (adapter/out)
                → PostgreSQL / Redis
        ← Retorno mapeado para DTO de resposta
    ← HTTP Response
```

**Padrão Strategy** — categorização automática de transações. Cada `CategorizationRule` implementa `TransactionCategorizationStrategy`. O `CategorizationService` itera pelas regras até encontrar correspondência. Novas categorias são adicionadas sem alterar código existente.

**Padrão Template Method** — jobs de sincronização. O `AbstractSyncJob` define o fluxo fixo (buscar conta → gerar transações → salvar → invalidar cache → publicar SSE). Subclasses especializam apenas a geração de dados por tipo de conta.

---

## Estrutura de pastas

```
open-finance-api/
├── src/
│   ├── main/
│   │   ├── java/com/pedroodake/openfinance/
│   │   │   │
│   │   │   ├── adapters/
│   │   │   │   ├── in/
│   │   │   │   │   └── web/
│   │   │   │   │       ├── AuthController.java
│   │   │   │   │       ├── AccountController.java
│   │   │   │   │       ├── TransactionController.java
│   │   │   │   │       ├── StatisticsController.java
│   │   │   │   │       ├── ConsentController.java
│   │   │   │   │       ├── ReportController.java
│   │   │   │   │       └── NotificationController.java
│   │   │   │   │
│   │   │   │   └── out/
│   │   │   │       └── persistence/
│   │   │   │           ├── UserRepository.java
│   │   │   │           ├── AccountRepository.java
│   │   │   │           ├── TransactionRepository.java
│   │   │   │           ├── ConsentRepository.java
│   │   │   │           └── ReportJobRepository.java
│   │   │   │
│   │   │   ├── application/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── BankAccount.java
│   │   │   │   │   ├── Transaction.java
│   │   │   │   │   ├── Consent.java
│   │   │   │   │   ├── ReportJob.java
│   │   │   │   │   └── enums/
│   │   │   │   │       ├── AccountType.java        # CORRENTE, POUPANCA, CARTAO
│   │   │   │   │       ├── Category.java           # ALIMENTACAO, TRANSPORTE, LAZER...
│   │   │   │   │       ├── ConsentScope.java       # SALDO, TRANSACOES, FATURA
│   │   │   │   │       ├── ConsentStatus.java      # ATIVO, REVOGADO, EXPIRADO
│   │   │   │   │       └── ReportStatus.java       # PENDENTE, PROCESSANDO, CONCLUIDO, ERRO
│   │   │   │   │
│   │   │   │   ├── dto/
│   │   │   │   │   ├── request/
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   │   ├── CreateAccountRequest.java
│   │   │   │   │   │   ├── UpdateCategoryRequest.java
│   │   │   │   │   │   └── ReportRequest.java
│   │   │   │   │   └── response/
│   │   │   │   │       ├── AuthResponse.java
│   │   │   │   │       ├── AccountResponse.java
│   │   │   │   │       ├── TransactionResponse.java
│   │   │   │   │       ├── StatisticsSummaryResponse.java
│   │   │   │   │       ├── CategoryStatisticsResponse.java
│   │   │   │   │       ├── PatrimonyResponse.java
│   │   │   │   │       ├── ConsentResponse.java
│   │   │   │   │       └── ReportJobResponse.java
│   │   │   │   │
│   │   │   │   ├── service/
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── AccountService.java
│   │   │   │   │   ├── TransactionService.java
│   │   │   │   │   ├── StatisticsService.java
│   │   │   │   │   ├── ConsentService.java
│   │   │   │   │   ├── ReportService.java
│   │   │   │   │   ├── SyncService.java
│   │   │   │   │   ├── NotificationService.java
│   │   │   │   │   └── categorization/
│   │   │   │   │       ├── TransactionCategorizationStrategy.java
│   │   │   │   │       ├── FoodCategorizationRule.java
│   │   │   │   │       ├── TransportCategorizationRule.java
│   │   │   │   │       ├── LeisureCategorizationRule.java
│   │   │   │   │       ├── HealthCategorizationRule.java
│   │   │   │   │       ├── IncomeCategorizationRule.java
│   │   │   │   │       └── CategorizationService.java
│   │   │   │   │
│   │   │   │   └── scheduler/
│   │   │   │       └── SyncScheduler.java
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── AsyncConfig.java
│   │   │   │   ├── CacheConfig.java
│   │   │   │   ├── OpenApiConfig.java
│   │   │   │   └── security/
│   │   │   │       ├── JwtService.java
│   │   │   │       └── JwtAuthFilter.java
│   │   │   │
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── ResourceNotFoundException.java
│   │   │       ├── ConflictException.java
│   │   │       └── UnauthorizedException.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/migration/
│   │           ├── V1__create_users.sql
│   │           ├── V2__create_accounts.sql
│   │           ├── V3__create_transactions.sql
│   │           ├── V4__create_consents.sql
│   │           └── V5__create_report_jobs.sql
│   │
│   └── test/
│       └── java/com/pedroodake/openfinance/
│           ├── application/
│           │   ├── service/
│           │   │   ├── CategorizationServiceTest.java
│           │   │   └── StatisticsServiceTest.java
│           └── adapters/
│               └── in/web/
│                   ├── AccountControllerIntegrationTest.java
│                   └── TransactionControllerIntegrationTest.java
│
├── docker-compose.yml
├── Dockerfile
├── .env.example
├── pom.xml
└── README.md
```

---

## Como rodar localmente

**Pré-requisitos:** Java 21, Maven 3.9+, Docker e Docker Compose

```bash
# Clonar o repositório
git clone https://github.com/pe-odake/open-finance-api.git
cd open-finance-api

# Subir PostgreSQL e Redis com Docker
docker-compose up -d

# Configurar variáveis de ambiente
cp .env.example .env
# Editar .env com as configurações do banco e chave JWT

# Rodar a aplicação
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

A API estará disponível em `http://localhost:8080`
Documentação Swagger UI: `http://localhost:8080/swagger-ui.html`

```bash
# Rodar os testes
./mvnw test
```

---

## Endpoints

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | `/auth/register` | Cadastro de novo usuário | Público |
| POST | `/auth/login` | Login — retorna JWT e refresh token | Público |
| POST | `/auth/refresh` | Renova o access token | Público |
| GET | `/accounts` | Lista contas do usuário autenticado | JWT |
| POST | `/accounts` | Adiciona nova conta | JWT |
| DELETE | `/accounts/{id}` | Remove uma conta | JWT |
| POST | `/accounts/{id}/sync` | Sincronização manual de uma conta | JWT |
| GET | `/transactions` | Lista transações com filtros e paginação | JWT |
| PATCH | `/transactions/{id}/category` | Altera categoria de uma transação | JWT |
| GET | `/statistics/summary` | Resumo mensal de entradas, saídas e saldo | JWT |
| GET | `/statistics/categories` | Gastos por categoria em um período | JWT |
| GET | `/statistics/patrimony` | Evolução patrimonial mensal | JWT |
| GET | `/consents` | Lista consentimentos ativos e histórico | JWT |
| DELETE | `/consents/{id}` | Revoga um consentimento | JWT |
| POST | `/reports/request` | Solicita geração de extrato (assíncrono) | JWT |
| GET | `/reports/{id}/status` | Verifica status do job de geração | JWT |
| GET | `/reports/{id}/download` | Faz download do extrato gerado | JWT |
| GET | `/notifications/stream` | SSE — stream de notificações em tempo real | JWT |

## Autor

**Pedro Odake**
[GitHub](https://github.com/pe-odake) · [LinkedIn](https://linkedin.com/in/pedro-odake/) · [pedroodake.com](https://pedroodake.com)
