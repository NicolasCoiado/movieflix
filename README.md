# Movieflix API

API RESTful para gerenciamento de filmes, categorias, plataformas de streaming e autenticação de usuários, que também permite gerar pôsters de filmes usando a API da OpenAI, desenvolvida em Spring Boot.

---

## Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)

---

## Estrutura de Endpoints


### **Autenticação**

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/movieflix/auth/register` | Registra um novo usuário. Retorna `UserResponse`. |
| POST | `/movieflix/auth/login` | Autentica usuário e retorna token JWT (`LoginResponse`). |


### **Filmes (Movies)**

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/movieflix/movies` | Salva um novo filme. Retorna `MovieResponse`. |
| GET | `/movieflix/movies` | Retorna todos os filmes cadastrados. |
| GET | `/movieflix/movies/{id}` | Retorna um filme específico pelo ID. |
| PUT | `/movieflix/movies/{id}` | Atualiza um filme existente pelo ID. |
| PATCH | `/movieflix/movies/{id}` | Edita parcialmente um filme existente. |
| GET | `/movieflix/movies/search?categoriesIds=1,2` | Retorna filmes filtrados por categorias. |
| DELETE | `/movieflix/movies/{id}` | Deleta um filme pelo ID. |
| POST | `/movieflix/movies/generate/{id}` | Gera uma imagem para um filme específico. |

> **Observação:** Endpoints de Movies exigem autenticação (Bearer Token).

### **Categorias (Categories)**

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/movieflix/categories` | Retorna todas as categorias. |
| POST | `/movieflix/categories` | Salva uma nova categoria. |
| GET | `/movieflix/categories/{id}` | Retorna uma categoria pelo ID. |
| PUT | `/movieflix/categories/{id}` | Atualiza uma categoria existente pelo ID. |
| DELETE | `/movieflix/categories/{id}` | Deleta uma categoria pelo ID. |

> **Observação:** Endpoints de Categories exigem autenticação (Bearer Token).


### **Plataformas de Streaming (Streaming)**

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/movieflix/streaming` | Retorna todas as plataformas de streaming. |
| POST | `/movieflix/streaming` | Salva uma nova plataforma de streaming. |
| GET | `/movieflix/streaming/{id}` | Retorna uma plataforma de streaming pelo ID. |
| PUT | `/movieflix/streaming/{id}` | Atualiza uma plataforma existente pelo ID. |
| DELETE | `/movieflix/streaming/{id}` | Deleta uma plataforma pelo ID. |

> **Observação:** Endpoints de Streaming exigem autenticação (Bearer Token).

---

## Documentação da API

A documentação completa da API está disponível via Swagger/OpenAPI nos endereços:

`/swagger-ui.html` ou `/api/api-docs`.

---

## Como Rodar o Projeto:

### 1. Clonar o Repositório

```bash
git clone https://github.com/NicolasCoiado/movieflix.git
cd /movieflix
```
### 2. Configurar Variáveis de Ambiente
Crie um arquivo .env na raiz do projeto com as seguintes variáveis:

```.dotenv
# Banco de dados
DB_NAME=movieflix
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
DB_PORTS=5432:5432
DB_VOLUMES=diretório-escolhido
CONTAINER_NAME=movieflix-postgres

# JWT
SECRET_JWT=sua_chave_secreta

# OpenAI API Key
OPENAI_API_KEY=sua_chave_openai

# Diretório de imagens
PATH_IMAGES=C:/Users/seu_usuario/Documents/Temporary
```

### 3. Preencher o docker-compose.yaml

O arquivo `docker-compose.yaml` já está configurado para utilizar as variáveis do `.env`:
```docker-compose.yaml
services:
  postgres:
    image: postgres
    container_name: ${CONTAINER_NAME}
    environment:
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    ports:
      - ${DB_PORTS}
    volumes:
      - ${DB_VOLUMES}
```
> Ele irá criar um container com PostgreSQL usando as variáveis que você definiu.

### 4. Preencher o application.yaml

O arquivo `application.yaml` utiliza variáveis do `.env` e já está configurado para o Spring Boot:

```yaml
spring:
  application:
    name: movieflix
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}

  datasource:
    url: jdbc:postgresql://localhost:5432/movieflix
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    show-sql: true

  flyway:
    enabled: true

springdoc:
  api-docs:
    path: /api/api-docs
  swagger-ui:
    path: /swagger/index.html

movieflix:
  security:
    secret: ${SECRET_JWT}

path:
  images: ${PATH_IMAGES}
```
> Certifique-se de que todas as variáveis do `.env` estejam corretas, especialmente o caminho das imagens e credenciais do banco.

### 5. Rodar o Docker Compose

No terminal, execute:

`docker-compose up -d`

Isso irá:
* Baixar a imagem do PostgreSQL (caso não exista);
* Criar e iniciar o container movieflix-postgres;
* Mapear portas e volumes conforme definido.

**Pronto, o projeto já pode ser executado na sua IDE!**