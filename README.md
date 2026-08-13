# Sistema de Empréstimos de Livros

API REST para gerenciamento de empréstimos de livros, usuários e acervo, desenvolvida com **Spring Boot 4.0.5** e **Java 21**.

---

## 📋 Funcionalidades

### 👤 Usuários
- **Listar todos** os usuários
- **Buscar por ID**
- **Criar** novo usuário
- **Atualizar** dados do usuário
- **Deletar** usuário (soft delete)

### 📚 Livros
- **Buscar por ID**
- **Criar** novo livro (título, autor, ISBN, gênero, ano de publicação)
- **Deletar** livro (soft delete)
- **Restaurar** livro deletado

### 🔄 Empréstimos
- **Listar todos** os empréstimos
- **Listar empréstimos ativos**
- **Buscar por ID**
- **Buscar empréstimos por usuário**
- **Histórico de empréstimos** do usuário (ordenado por data)
- **Empréstimos em atraso**
- **Usuários com empréstimos em atraso**
- **Criar empréstimo** (validações: usuário ativo, livro disponível, limite de 3 empréstimos, multas pendentes)
- **Devolver empréstimo** (calcula multa automaticamente: R$ 2,50/dia de atraso)
- **Total de multas pendentes** por usuário

---

## 🛠️ Stack Tecnológica

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 4.0.5 |
| Spring Data JPA | - |
| Flyway | - |
| MySQL | 8+ |
| SpringDoc OpenAPI (Swagger) | 3.0.2 |
| Lombok | 1.18.30 |
| Maven | - |

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+
- MySQL 8+

### Configuração do Banco de Dados

1. Crie o banco no MySQL:
```sql
CREATE DATABASE sistema_emprestimo;
```

2. Configure `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sistema_emprestimo?useTimezone=true&serverTimezone=UTC
    username: root
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
```

> As migrações Flyway criam as tabelas automaticamente na inicialização.

### Executar a aplicação
```bash
# Compilar e rodar testes
mvn clean test

# Rodar a aplicação
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Documentação Swagger
Acesse: `http://localhost:8080/swagger-ui.html`

---

## 📖 Endpoints da API

### Usuários (`/user`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/user` | Lista todos os usuários |
| GET | `/user/{id}` | Busca usuário por ID |
| POST | `/user` | Cria novo usuário |
| PUT | `/user/{id}` | Atualiza usuário |
| DELETE | `/user/{id}/delete` | Deleta usuário (soft delete) |

**Exemplo de criação:**
```json
POST /user
{
  "name": "João Silva",
  "email": "joao@email.com"
}
```

---

### Livros (`/books`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/books/{id}` | Busca livro por ID |
| POST | `/books` | Cria novo livro |
| DELETE | `/books/{id}/delete` | Deleta livro (soft delete) |
| PATCH | `/books/{id}/restore` | Restaura livro deletado |

**Exemplo de criação:**
```json
POST /books
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "genre": "Tecnologia",
  "publicationYear": 2008,
  "active": true
}
```

---

### Empréstimos (`/loans`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/loans` | Lista todos os empréstimos |
| GET | `/loans/active` | Lista empréstimos ativos |
| GET | `/loans/{loanId}` | Busca empréstimo por ID |
| GET | `/loans/user/{id}` | Lista empréstimos de um usuário |
| GET | `/loans/{userId}/active` | Empréstimos ativos do usuário |
| GET | `/loans/{userId}/history` | Histórico de empréstimos do usuário |
| GET | `/loans/overdue/loans` | Empréstimos em atraso |
| GET | `/loans/overdue/users` | Usuários com empréstimos em atraso |
| GET | `/loans/pending/{userId}/fines` | Total de multas pendentes do usuário |
| POST | `/loans/{userId}/{bookId}` | Cria novo empréstimo |
| POST | `/loans/{loanId}/return` | Devolve empréstimo |

---

## ⚙️ Regras de Negócio

### Empréstimos
- **Limite:** Máximo de 3 empréstimos ativos por usuário
- **Prazo:** 7 dias para devolução
- **Multa:** R$ 2,50 por dia de atraso
- **Bloqueio automático:** Usuário com empréstimo em atraso fica bloqueado

### Validações na Criação de Empréstimo
1. Usuário deve estar **ATIVO** (não INACTIVE nem BLOCKED)
2. Livro deve estar **DISPONÍVEL** (status AVAILABLE e active=true)
3. Usuário não pode ter **multas pendentes**
4. Usuário não pode ter **atingido o limite** de 3 empréstimos
5. Usuário não pode ter **empréstimos em atraso** (caso contrário é bloqueado)

### Status de Entidades

**UserStatus:** `ACTIVE`, `INACTIVE`, `BLOCKED`

**BookStatus:** `AVAILABLE`, `LOANED`, `INACTIVE`

**LoanStatus:** `ACTIVE`, `RETURNED`, `OVERDUE`

---

## 🗃️ Estrutura do Projeto

```
src/main/java/loan/system/com/
├── Book/
│   ├── controller/BookController.java
│   ├── domain/Book.java
│   ├── dto/BookRequestDTO.java, BookResponseDTO.java
│   ├── repository/BookRepository.java
│   ├── service/BookService.java
│   ├── BookMapper/MapperBook.java
│   └── BookStatus.java
├── Loan/
│   ├── controller/LoanController.java
│   ├── domain/Loan.java
│   ├── dto/LoanRequestDTO.java, LoanResponseDTO.java
│   ├── repository/LoanRepository.java
│   ├── service/LoanService.java, LoanRuleService.java
│   ├── LoanMapper/LoanMapper.java
│   └── LoanStatus.java
├── User/
│   ├── controller/UserController.java
│   ├── domain/User.java
│   ├── dto/UserRequestDTO.java, UserResponseDTO.java
│   ├── repository/UserRepository.java
│   ├── service/UserService.java
│   ├── MapperUser/UserMapper.java
│   └── UserStatus.java
└── exception/
    ├── GlobalExceptionHandler.java
    ├── NotFoundException.java
    ├── ConflictRequestException.java
    ├── BadRequestException.java
    ├── NoContentException.java
    └── ErrorResponse.java
```

---

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar com cobertura
mvn clean test jacoco:report
```

---

## 📦 Build para Produção

```bash
mvn clean package -DskipTests
```

O JAR será gerado em `target/system-0.0.1-SNAPSHOT.jar`.

---

## 🤝 Contribuição

1. Fork o projeto
2. Crie sua feature branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 👨‍💻 Autor

Desenvolvido por Henrique