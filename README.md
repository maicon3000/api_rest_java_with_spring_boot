# API REST Java com Spring Boot

## Visão Geral

Este projeto é uma API RESTful desenvolvida em Java utilizando o framework Spring Boot. Ele foi criado seguindo as melhores práticas de desenvolvimento de software, com foco em Clean Code, design patterns, e documentação robusta. A API interage com um banco de dados PostgreSQL e utiliza uma série de ferramentas e frameworks para garantir qualidade e manutenibilidade.

## Funcionalidades

- **CRUD Completo**: Realize operações de Create, Read, Update e Delete nas entidades Profissionais e Contatos.
- **Exclusão Lógica**: Implementação de deleção lógica para evitar perda de dados sensíveis.
- **Validação Customizada**: Uso de validações específicas com mensagens de erro claras e precisas.
- **Documentação Automática**: Swagger UI para visualização e interação com a API em tempo real.

## Tecnologias e Ferramentas utilizadas

### 1. **Spring Boot**
   - **Descrição**: O Spring Boot é um framework Java que simplifica a criação de aplicações stand-alone, de produção, baseadas em Spring. Ele proporciona uma configuração padrão para projetos Spring, reduzindo a quantidade de configuração manual e XML.
   - **Funcionalidade**: Serve como o backbone da aplicação, gerenciando a injeção de dependências, configuração e ciclo de vida dos beans.

### 2. **Hibernate**
   - **Descrição**: Hibernate é um framework ORM (Object-Relational Mapping) que facilita a manipulação de dados relacionais em Java, mapeando objetos Java para tabelas do banco de dados.
   - **Funcionalidade**: Utilizado para persistência de dados, facilitando a interação entre a aplicação e o banco de dados PostgreSQL.

### 3. **Java SE 22**
   - **Descrição**: A última versão do Java, que traz melhorias de desempenho, novas APIs e outras funcionalidades.
   - **Funcionalidade**: Linguagem de programação principal usada para desenvolver toda a lógica da aplicação.

### 4. **PostgreSQL & PgAdmin 4**
   - **Descrição**: PostgreSQL é um sistema gerenciador de banco de dados objeto-relacional de código aberto. PgAdmin 4 é uma ferramenta de gerenciamento e administração para PostgreSQL.
   - **Funcionalidade**: Banco de dados utilizado para armazenar todas as informações da aplicação. PgAdmin 4 é utilizado para gerenciar e visualizar o banco de dados.


### 5. **Postman**
   - **Descrição**: Postman é uma ferramenta de colaboração de API que facilita o desenvolvimento e o teste de APIs.
   - **Funcionalidade**: Utilizado para testar endpoints da API durante o desenvolvimento, garantindo que cada rota funcione corretamente.
   - ![image](https://github.com/user-attachments/assets/5602b449-ca1e-4cf5-8264-02f91035f68c)


### 6. **SpringDoc e Swagger**
   - **Descrição**: SpringDoc é uma biblioteca que gera automaticamente a documentação OpenAPI (Swagger) para APIs RESTful baseadas em Spring.
   - **Funcionalidade**: Gera uma interface gráfica onde é possível visualizar e interagir com os endpoints da API.

https://github.com/user-attachments/assets/f0a593b7-971c-4f6b-87bf-b8cb0d6946ed

### 7. **FlywayDB**
   - **Descrição**: Flyway é uma ferramenta de migração de banco de dados baseada em SQL.
   - **Funcionalidade**: Utilizado para gerenciar e versionar as alterações no esquema do banco de dados, garantindo que todas as instâncias da aplicação tenham a mesma estrutura de banco de dados.

https://github.com/user-attachments/assets/b697f533-d7b0-4b7e-9f18-9d21e6930349

### 8. **Lombok**
   - **Descrição**: Lombok é uma biblioteca Java que reduz o código boilerplate, como getters, setters, e construtores, através de anotações.
   - **Funcionalidade**: Facilita a criação de entidades e classes de serviço, tornando o código mais limpo e fácil de manter.

### 9. **DozerMapper**
   - **Descrição**: Dozer é um framework de mapeamento de objetos que simplifica a conversão de objetos de um tipo para outro.
   - **Funcionalidade**: Utilizado para converter entre entidades do banco de dados e DTOs, garantindo que apenas os dados necessários sejam expostos pela API.

### 10. **JUnit**
   - **Descrição**: JUnit é um framework para escrita e execução de testes unitários em Java.
   - **Funcionalidade**: Utilizado para garantir que cada unidade da aplicação (métodos, classes) funcione como esperado. Contém testes que validam a lógica de negócio e garantem a qualidade do código.

### 11. **JavaDoc**
   - **Descrição**: JavaDoc é uma ferramenta para geração automática de documentação de código Java.
   - **Funcionalidade**: Documenta a API, facilitando a compreensão do código por outros desenvolvedores e melhorando a manutenção do projeto.

https://github.com/user-attachments/assets/885616cb-6ed0-4d78-ae69-97c262ce2d58

### 12. JaCoCo Maven Plugin
   - **Descrição**: JaCoCo (Java Code Coverage) é uma biblioteca de código aberto usada para medir a cobertura de código Java durante a execução de testes.
   - **Funcionalidade**: O plugin JaCoCo Maven é integrado ao ciclo de vida do Maven para gerar relatórios detalhados de cobertura de código, permitindo que os desenvolvedores identifiquem partes do código que não foram cobertas pelos testes. Isso ajuda a garantir que o código tenha uma cobertura adequada de testes, aumentando a confiabilidade e a qualidade do software.

https://github.com/user-attachments/assets/fb254bf8-77ba-4cf9-b01d-3467457e9f54

### 13. **Clean Code & Design Patterns**
   - **Descrição**: Clean Code é uma filosofia de desenvolvimento que enfatiza a escrita de código limpo, legível e fácil de manter. Design patterns são soluções comprovadas para problemas comuns de design de software.
   - **Funcionalidade**: Padrões como MVC, DAO, DTO, Singleton, Adapter, Strategy e Facade são utilizados para estruturar a aplicação, melhorando sua manutenção e escalabilidade.


## Como Rodar o Projeto

### Pré-requisitos

- **Java SE 22**: Certifique-se de que o JDK esteja instalado.
- **Maven**: Gerenciador de dependências e automação de builds.
- **PostgreSQL**: Banco de dados utilizado pela aplicação.
- **PgAdmin 4**: Ferramenta de administração para PostgreSQL.

### Passos para Executar

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/maicon3000/api_rest_java_with_spring_boot.git
   cd api_rest_java_with_spring_boot
   ```
   
2. **Configuração do Banco de Dados**:
- Configure as credenciais do PostgreSQL no arquivo application.properties.
- Execute o PgAdmin 4 e crie o banco de dados com o nome: api_rest_java_with_spring_boot.

3. **Rodando o Projeto**:
   ```bash
   mvn spring-boot:run
   ```
   
4. **Acessando o Swagger**:
- Após a inicialização da aplicação, acesse o Swagger UI através do endereço:
   ```bash
   http://localhost:8080/swagger-ui.html
   ```

   
## Testes Unitários
Para rodar os testes unitários, utilize o comando:
```bash
mvn test
```
Ou então utilize o plugin JaCoCo Maven Plugin e rode em sua IDE Spring Boot.

Os testes garantem a qualidade do código e validam que todas as funcionalidades da API estão funcionando corretamente.

## Contribuindo
Sinta-se à vontade para abrir issues ou enviar pull requests. Qualquer feedback é bem-vindo!

## Licença
Este projeto é licenciado sob a MIT License.

Feito com :heart: por [Maicon Moraes](https://github.com/maicon3000)
