# Finanças+ - Aplicação de Gerenciamento de Finanças Pessoais

Uma aplicação web desenvolvida em **Java com Spring Boot** para gerenciar finanças pessoais, permitindo o registro de receitas e despesas, visualização de saldos e análise de gastos por categoria.

## Características

- **Autenticação de Usuário:** Login e cadastro de novos usuários
- **Dashboard:** Visualização do saldo atual, receitas e despesas totais
- **Gerenciamento de Transações:** Adicionar, editar e deletar receitas e despesas
- **Categorização:** Organizar transações por categorias predefinidas
- **Relatórios:** Análise mensal de gastos com gráficos interativos
- **Interface Responsiva:** Design moderno e intuitivo
- **Banco de Dados H2:** Armazenamento em memória (sem necessidade de MySQL)

## Tecnologias Utilizadas

- **Backend:** Spring Boot 3.2.0
- **Frontend:** HTML5, CSS3, JavaScript, Thymeleaf
- **Banco de Dados:** H2 Database
- **ORM:** Spring Data JPA
- **Gráficos:** Chart.js
- **Build:** Maven

## Pré-requisitos

- **Java 17 ou superior**
- **Maven 3.6 ou superior**

## Instalação e Execução

### 1. Clonar ou extrair o projeto

```bash
cd financas_plus
```

### 2. Compilar o projeto

```bash
mvn clean install
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 4. Acessar o console H2 (opcional)

Para visualizar o banco de dados H2, acesse: `http://localhost:8080/h2-console`

**Credenciais padrão:**
- URL JDBC: `jdbc:h2:mem:financasdb`
- Username: `sa`
- Password: (deixar em branco)

## Uso da Aplicação

### 1. Criar uma Conta

- Acesse a página de registro
- Preencha os dados: username, email, nome completo e senha
- Clique em "Cadastrar"

### 2. Fazer Login

- Acesse a página de login
- Insira seu username e senha
- Clique em "Entrar"

### 3. Dashboard

- Visualize seu saldo atual, receitas e despesas totais
- Veja um gráfico de gastos por categoria
- Acesse as transações recentes

### 4. Gerenciar Transações

- Clique em "Transações" na navegação
- Clique em "+ Adicionar Transação" para criar uma nova
- Selecione o tipo (Receita ou Despesa), categoria, valor e data
- Edite ou delete transações conforme necessário

### 5. Visualizar Relatórios

- Clique em "Relatórios" na navegação
- Selecione o mês desejado
- Visualize o resumo mensal e gráficos de gastos por categoria

## Estrutura do Projeto

```
financas_plus/
├── src/
│   ├── main/
│   │   ├── java/com/financasplus/
│   │   │   ├── FinancasPlusApplication.java      # Classe principal
│   │   │   ├── controller/                       # Controllers
│   │   │   ├── model/                            # Entidades JPA
│   │   │   ├── repository/                       # Interfaces Repository
│   │   │   └── service/                          # Serviços de negócio
│   │   └── resources/
│   │       ├── application.properties            # Configurações
│   │       └── templates/                        # Templates Thymeleaf
│   └── test/                                     # Testes unitários
├── pom.xml                                       # Configuração Maven
└── README.md                                     # Este arquivo
```

## Funcionalidades Detalhadas

### Entidades

1. **User:** Representa um usuário da aplicação
2. **Category:** Categorias de transações (Alimentação, Transporte, etc.)
3. **Transaction:** Transações financeiras (receitas e despesas)

### Controllers

1. **HomeController:** Gerencia login, registro e dashboard
2. **TransactionController:** CRUD de transações
3. **ReportController:** Geração de relatórios e análises

### Services

1. **UserService:** Lógica de negócio para usuários
2. **TransactionService:** Lógica de negócio para transações
3. **CategoryService:** Lógica de negócio para categorias

## Segurança

⚠️ **Nota:** Esta é uma aplicação educacional. Para produção, implemente:

- Criptografia de senhas (BCrypt)
- HTTPS/SSL
- Validação mais rigorosa de entrada
- Proteção contra CSRF
- Rate limiting

## Banco de Dados

O projeto utiliza **H2 Database** em modo em memória. Os dados são perdidos quando a aplicação é reiniciada.

Para persistência permanente, modifique `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./data/financasdb
```

## Troubleshooting

### Erro: "Port 8080 already in use"

Altere a porta em `application.properties`:

```properties
server.port=8081
```

### Erro ao compilar

Certifique-se de ter Java 17+ instalado:

```bash
java -version
```

## Contribuições

Este projeto foi desenvolvido como atividade de Programação de Servidores Web.

## Autor

Desenvolvido para fins educacionais.

## Licença

Este projeto é fornecido como está para fins de aprendizado.

---

**Versão:** 1.0.0  
**Data:** 2025
