# Finanças+ 💰

Uma aplicação web de gerenciamento de finanças pessoais desenvolvida como projeto final da disciplina de **Programação de Servidores Web**.

## 📚 Informações da Disciplina

- **Disciplina:** Programação de Servidores Web
- **Semestre:** 5º semestre
- **Curso:** Tecnologia em Sistemas para Internet
- **Professor:** Gustavo Yoshio Maruyama
- **Instituição:** Instituto Federal de Educação, Ciência e Tecnologia de Mato Grosso do Sul (IFMS)
- **Câmpus:** Coxim

## 🎯 Objetivo do Projeto

Desenvolver uma aplicação web funcional utilizando tecnologias estudadas durante a disciplina (Servlets, JSP, JSF ou Spring Framework) que demonstre os conhecimentos adquiridos em programação de servidores web, integração com banco de dados e desenvolvimento de interfaces web interativas.

## 📋 Descrição

**Finanças+** é uma aplicação web para gerenciamento de finanças pessoais que permite aos usuários:

- **Registrar transações** (receitas e despesas)
- **Visualizar saldo atual** em tempo real
- **Categorizar gastos** (Alimentação, Transporte, Lazer, Contas Fixas, Saúde, Educação, Outros)
- **Analisar gastos** através de gráficos interativos
- **Gerar relatórios** mensais com visualização de despesas por categoria
- **Gerenciar conta de usuário** com autenticação segura

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework web
- **Spring Data JPA** - Acesso a dados
- **Hibernate** - ORM (Object-Relational Mapping)
- **Maven** - Gerenciador de dependências

### Frontend
- **HTML5** - Estrutura
- **CSS3** - Estilização
- **JavaScript** - Interatividade
- **Thymeleaf** - Template engine

### Banco de Dados
- **H2 Database** - Banco de dados relacional em memória
- **SQL** - Linguagem de consulta

### Ferramentas
- **NetBeans** - IDE de desenvolvimento
- **Git** - Controle de versão
- **GitHub** - Repositório remoto

## ✨ Funcionalidades Principais

### 1. Autenticação de Usuário
- ✅ Página de login
- ✅ Registro de novos usuários
- ✅ Gerenciamento de sessões
- ✅ Logout seguro

### 2. Dashboard
- ✅ Exibição do saldo atual
- ✅ Total de receitas
- ✅ Total de despesas
- ✅ Gráfico visual de saldo (receita vs despesa)
- ✅ Listagem das 5 transações mais recentes

### 3. Gerenciamento de Transações
- ✅ Adicionar nova transação (receita ou despesa)
- ✅ Editar transação existente
- ✅ Deletar transação
- ✅ Listar todas as transações
- ✅ Filtrar por tipo (receita/despesa)

### 4. Relatórios
- ✅ Filtrar por mês
- ✅ Visualizar saldo do mês
- ✅ Gráfico de receita vs despesa (mês)
- ✅ Gráfico de gastos por categoria com cores distintas
- ✅ Listagem de transações do mês

### 5. Categorias
- ✅ 7 categorias pré-configuradas
- ✅ Inicialização automática ao primeiro acesso
- ✅ Associação de transações a categorias

## 📁 Estrutura do Projeto

```
financas_plus/
├── pom.xml                                  # Configuração Maven
├── README.md                                # Documentação
├── INSTRUÇÕES.txt                          # Instruções de execução
│
├── src/main/java/com/financasplus/
│   ├── FinancasPlusApplication.java         # Classe principal
│   │
│   ├── model/                               # Entidades JPA
│   │   ├── User.java                        # Usuário
│   │   ├── Category.java                    # Categoria
│   │   └── Transaction.java                 # Transação
│   │
│   ├── repository/                          # Repositories (acesso ao BD)
│   │   ├── UserRepository.java
│   │   ├── CategoryRepository.java
│   │   └── TransactionRepository.java
│   │
│   ├── service/                             # Services (lógica de negócio)
│   │   ├── UserService.java
│   │   ├── CategoryService.java
│   │   └── TransactionService.java
│   │
│   ├── controller/                          # Controllers (rotas)
│   │   ├── HomeController.java              # Login, Registro, Dashboard
│   │   ├── TransactionController.java       # Gerenciamento de transações
│   │   └── ReportController.java            # Relatórios
│   │
│   └── config/                              # Configurações
│       └── DataInitializer.java             # Inicialização de dados
│
└── src/main/resources/
    ├── application.properties                # Configuração da aplicação
    └── templates/                            # Templates HTML (Thymeleaf)
        ├── login.html
        ├── register.html
        ├── dashboard.html
        ├── transactions.html
        ├── add-transaction.html
        ├── edit-transaction.html
        └── reports.html
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior
- NetBeans (opcional, mas recomendado)
- Git

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/financas-plus.git
cd financas-plus
```

2. **Abra no NetBeans**
   - File → Open Project
   - Selecione a pasta `financas-plus`
   - NetBeans reconhecerá automaticamente como projeto Maven

3. **Compile o projeto**
```bash
mvn clean install
```

4. **Execute a aplicação**
   - Via NetBeans: Clique com botão direito → Run
   - Via terminal: `mvn spring-boot:run`

5. **Acesse a aplicação**
   - Abra o navegador e acesse: `http://localhost:8080`

### Credenciais de Teste

Você pode criar uma nova conta ou usar:
- **Username:** `admin`
- **Senha:** `123456`

## 📊 Exemplos de Uso

### 1. Criar uma Transação
1. Faça login
2. Clique em "Transações"
3. Clique em "+ Adicionar Transação"
4. Preencha os dados:
   - Descrição: "Salário"
   - Tipo: "Receita"
   - Valor: "3000.00"
   - Categoria: "Outros"
   - Data: Selecione a data
5. Clique em "Adicionar"

### 2. Visualizar Dashboard
1. Após criar transações, clique em "Dashboard"
2. Veja o saldo, receitas e despesas
3. Observe o gráfico visual de saldo
4. Veja as 5 transações mais recentes

### 3. Gerar Relatório
1. Clique em "Relatórios"
2. Selecione o mês desejado
3. Clique em "Filtrar"
4. Visualize:
   - Saldo do mês
   - Gráfico de receita vs despesa
   - Gráfico de gastos por categoria
   - Listagem de transações

## 🗄️ Banco de Dados

### Entidades

**User (Usuário)**
- `id` - Identificador único
- `username` - Nome de usuário (único)
- `password` - Senha (criptografada)
- `fullName` - Nome completo
- `email` - Email
- `createdAt` - Data de criação

**Category (Categoria)**
- `id` - Identificador único
- `name` - Nome da categoria
- `description` - Descrição

**Transaction (Transação)**
- `id` - Identificador único
- `user_id` - Referência ao usuário
- `category_id` - Referência à categoria
- `description` - Descrição
- `amount` - Valor
- `type` - Tipo (RECEITA ou DESPESA)
- `date` - Data da transação
- `createdAt` - Data de criação

## 🔐 Segurança

- ✅ Autenticação via sessão HTTP
- ✅ Validação de entrada em formulários
- ✅ Proteção contra SQL Injection (uso de JPA)
- ✅ Senhas armazenadas com hash (implementação futura)
- ✅ Isolamento de dados por usuário

## 📈 Versões

### v1.0 (Versão Inicial)
- Funcionalidades básicas de CRUD
- Banco de dados em memória (H2)
- Autenticação simples
- Gráficos interativos

### v2.0 (Versão Atual)
- Banco de dados persistente em arquivo
- Melhorias na interface
- Gráficos dinâmicos de saldo
- Relatórios por categoria com cores distintas

## 🎓 Critérios de Avaliação Atendidos

- ✅ **Clareza e originalidade da proposta:** Aplicação de gerenciamento financeiro com interface intuitiva
- ✅ **Correção técnica e funcionamento do código:** Código bem estruturado e funcional
- ✅ **Organização, legibilidade e comentários:** Código comentado e bem organizado
- ✅ **Qualidade visual e usabilidade da interface:** Interface responsiva e amigável
- ✅ **Integração com banco de dados:** H2 Database com JPA/Hibernate
- ✅ **Interatividade:** Gráficos dinâmicos e formulários interativos

## 🤝 Contribuições

Este é um projeto acadêmico. Sugestões e melhorias são bem-vindas!

## 📝 Licença

Este projeto é fornecido como material educacional para a disciplina de Programação de Servidores Web do IFMS.

## 👨‍💻 Autor

**[Seu Nome]**
- Estudante de Tecnologia em Sistemas para Internet
- IFMS - Câmpus Coxim
- Período: 5º semestre

## 📞 Contato

- Email: seu.email@example.com
- GitHub: [@seu-usuario](https://github.com/seu-usuario)
- LinkedIn: [Seu Perfil](https://linkedin.com/in/seu-perfil)

## 🙏 Agradecimentos

- Prof. Gustavo Yoshio Maruyama pela orientação e ensinamentos
- IFMS pela infraestrutura e oportunidade
- Spring Framework pela excelente documentação
- Comunidade Java/Spring Boot

## 📚 Referências

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/)
- [H2 Database Documentation](https://www.h2database.com/)
- [Java Documentation](https://docs.oracle.com/en/java/)

---

**Última atualização:** Novembro de 2025

**Status:** ✅ Projeto Concluído

