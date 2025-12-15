# 🏦 API de Consulta de Créditos

## Visão Geral do Projeto

Este projeto implementa uma API RESTful para consulta de créditos constituídos, utilizando **Spring Boot** no Back-end, **Angular** no Front-end e **Docker Compose** para orquestração de infraestrutura (PostgreSQL e Kafka).

O objetivo principal é permitir a consulta de créditos por número de NFS-e ou por número do crédito, retornando os dados em formato JSON. Além disso, todas as consultas são auditadas via mensageria Kafka.
* **Back-end:** Java, Spring Boot, Spring Data JPA, Hibernate.
* **Front-end:** Angular 17+ e responsividade.
* **Banco de Dados:** PostgreSQL (containerizado).
* **Containerização:** Docker e Docker Compose.
* **Mensageria:** Implementação do **Publisher Kafka** para auditoria de consultas.
* **Testes:** Cobertura de testes unitários na camada Service (JUnit e Mockito).

---

## 🛠️ Tecnologias Utilizadas

### Back-end
* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3.x
* **Persistência:** Spring Data JPA / Hibernate
* **Mensageria:** Spring Kafka
* **Testes:** JUnit 5, Mockito

### Front-end
* **Framework:** Angular 17+
* **Estilização:** CSS puro e responsividade via Flexbox/Media Queries.

### Infraestrutura
* **Orquestração:** Docker Compose
* **Banco de Dados:** PostgreSQL 15
* **Mensageria:** Kafka Broker e ZooKeeper

---

## 🚀 Como Rodar o Projeto (Instruções de Execução)

O projeto é configurado para ser executado totalmente via **Docker Compose**.

### Pré-requisitos
* **Docker** e **Docker Compose** instalados e em execução.
* **Portas:** 5432 (PostgreSQL), 8080 (API), 9092 (Kafka), 4200 (Frontend) devem estar livres.

### Etapas de Execução

1.  **Clone o Repositório:**
    ```bash
    git clone [LINK_DO_SEU_REPOSITORIO]
    cd consultaCreditosAPI
    ```

2.  **Construa e Inicie os Contêineres:**
    O `docker-compose.yml` irá construir a API Spring Boot, o Front-end Angular e iniciar os serviços PostgreSQL, Kafka e ZooKeeper.
    ```bash
    # A flag --build garante que a API e o Front-end sejam compilados
    docker compose up --build
    ```
    *Aguarde a inicialização. O PostgreSQL será populado com os dados de teste via `db/init.sql`, e a API levará alguns segundos para se conectar ao DB e ao Kafka.*

3.  **Acesse o Front-end:**
    Abra seu navegador e acesse a URL:
    [http://localhost:4200](http://localhost:4200)

4.  **Teste a API (Opcional):**
    Você pode testar a API diretamente (ex: via Postman ou cURL):
    * **Buscar por NFS-e (Retorna 2 Créditos):**
        ```bash
        curl -X GET "http://localhost:8080/api/creditos/7891011"
        ```
    * **Buscar por Número do Crédito (Retorna 1 Crédito):**
        ```bash
        curl -X GET "http://localhost:8080/api/creditos/credito/654321"
        ```

---

## 💻 Estrutura da API RESTful

A API expõe dois endpoints principais para consulta:

| Método | Endpoint                                | Parâmetro | Descrição |
| :--- |:----------------------------------------| :--- | :--- |
| `GET` | `/api/creditos/{numeroNfse}`            | `numeroNfse` (String) | Retorna uma **lista** de créditos associados à NFS-e. |
| `GET` | `/api/creditos/credito/{numeroCredito}` | `numeroCredito` (String) | Retorna os detalhes de um **crédito específico**. |

**Códigos de Resposta:**
* `200 OK`: Consulta bem-sucedida.
* `404 Not Found`: Nenhum registro encontrado para o parâmetro fornecido.

---

## 🔑 Implementação e Padrões de Projeto

### Arquitetura Back-end
O Back-end segue o padrão **Arquitetura em Camadas (Layered Architecture)**, priorizando a separação de responsabilidades (SRP - Single Responsibility Principle):

* **`controller`**: Recebe requisições HTTP e delega.
* **`service`**: Contém a lógica de negócios e orquestração (Padrão **Singleton** implícito via `@Service`).
* **`repository`**: Abstração de acesso a dados (Padrão **Repository** via Spring Data JPA).
* **`messaging`**: Implementa a interface `MessagePublisher` (Padrão **DIP** - Dependency Inversion Principle).

### Mensageria (Kafka)

A integração com o Kafka foi implementada para fins de auditoria/log.

1.  A interface `MessagePublisher` permite a troca fácil de tecnologia (ex: para Azure Service Bus ou RabbitMQ).
2.  O `KafkaMessagePublisher` utiliza o `KafkaTemplate` para serializar um objeto `ConsultaEvent` (Java Record) em JSON e publicá-lo no tópico `creditos-consultados-topic` em **toda consulta** realizada no `CreditoService`.
3.  O contêiner Kafka é iniciado via Docker Compose e configurado no `application.yml` da API.

### Front-end Angular

O Front-end utiliza um componente principal (`consulta-credito.component`) que gerencia a entrada de dados (toggle entre busca por NFS-e ou Crédito) e a exibição tabular dos resultados. A **responsividade** é garantida através do uso de Flexbox e `overflow-x: auto` na tabela para telas móveis.

---

## 🧪 Testes Automatizados

O projeto possui cobertura de testes unitários, garantindo a qualidade do código.

### Testes Implementados
* **`CreditoServiceTest` (JUnit & Mockito):** Focado na lógica de negócios. Garante que os métodos de busca funcionem corretamente e, crucialmente, verifica se o `messagePublisher` é invocado em todas as buscas (**Verificação de Comportamento**).
