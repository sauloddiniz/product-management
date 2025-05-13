# Gerenciamento de Produtos (Product Management)

## 📋 Descrição

API REST para gerenciamento de produtos utilizando arquitetura hexagonal e princípios de Clean Architecture.

## 🏗️ Arquitetura

O projeto segue a **Arquitetura Hexagonal** (também conhecida como Ports and Adapters).

## 🛠️ Tecnologias

- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Cache
- Swagger/OpenAPI
- Flyway para migrations
- H2 Database
- JUnit 5 para testes
- Maven

## ✨ Features

- CRUD completo de produtos
- Categorização de produtos
- Cache para otimização de consultas
- Auditoria de dados (created_at, updated_at)
- Documentação API com Swagger
- Tratamento global de exceções
- Validações de domínio

## Guia de Uso

### Pré-requisitos para o Ambiente

Antes de começar, certifique-se de que o seu ambiente possui as seguintes ferramentas configuradas:

- **Java 17**: Certifique-se de ter o JDK (Java Development
  Kit). [Guia de instalação do Java](https://openjdk.org/install/).
- **Maven 3.6**: Necessário para o gerenciamento de dependências e compilação do
  projeto. [Guia de instalação do Maven](https://maven.apache.org/install.html).

---

### **Passos para Execução**

1. **Clonar o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/product-management.git
   cd product-management
   ```

2. **Gerar o arquivo JAR**:
   Na raiz do projeto, execute o comando abaixo para criar o JAR da aplicação:
   ```bash
   mvn clean package
   ```
   Após a execução, o arquivo JAR será gerado em: `target/product-management-0.0.1-SNAPSHOT.jar`

3. **Executar a aplicação**:
   Você tem duas opções para executar a aplicação:

   **Opção 1 - Via Maven**:
   ```bash
   mvn spring-boot:run
   ```

   **Opção 2 - Via JAR**:
   ```bash
   java -jar target/product-management-0.0.1-SNAPSHOT.jar
   ```
   A aplicacao nao tem configuracao de profile:

4. **Verificar se a aplicação está rodando**:

- [actuator - health](http://localhost:8080/product-management/actuator/health)
  Você deverá ver a resposta: `{"status":"UP"}`
---

### Documentação dos Endpoints

Com a aplicação em execução, a documentação da API estará disponível através do **Swagger**:

- **Ambiente local**:
    - [Swagger UI - Local](http://localhost:8080/product-management/swagger-ui/index.html)

---