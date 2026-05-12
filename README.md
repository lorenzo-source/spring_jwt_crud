# Spring Boot JWT CRUD API

A secured REST API built with Spring Boot 3, Spring Security, and JWT authentication, implementing a complete user management system with token-based access control.

---

## What it does

This project implements a production-style secured REST API with the following features:

- **User registration and login** with JWT access tokens
- **Refresh token flow** for session renewal without re-authentication
- **Full CRUD operations** on user entities
- **Soft delete** — users are never physically removed from the database, preserving data integrity and audit trails
- **Stateless authentication** — no server-side sessions, every request is authenticated via token
- **Request logging** via a dedicated logging filter

---

## Architecture

The project follows a clean layered architecture:

```
Controller  →  Service  →  Repository  →  Database
                ↑
           Security / JWT Filter Chain
```

- **Controller layer** — exposes REST endpoints, handles HTTP request/response
- **Service layer** — business logic, user operations, JWT lifecycle management
- **Repository layer** — Spring Data JPA, database access
- **Security layer** — Spring Security filter chain, JWT validation on every request
- **JWT layer** — token generation, validation, expiration, and refresh logic

---

## Technical decisions

**Stateless sessions** — Spring Security is configured with `SessionCreationPolicy.STATELESS`. No session is created or stored server-side. Every request must carry a valid JWT.

**Short-lived access tokens + long-lived refresh tokens** — access tokens expire in 15 minutes, refresh tokens in 7 days. This limits exposure if a token is compromised while keeping the user experience smooth.

**Refresh token stored in database** — on login the refresh token is persisted, allowing server-side invalidation if needed.

**BCrypt with strength 12** — passwords are never stored in plain text. BCrypt with cost factor 12 provides strong protection against brute force attacks.

**Soft delete pattern** — deletion sets a `dataCancellazione` timestamp instead of removing the record. This is standard practice in banking and financial systems where data must be preserved for audit and regulatory purposes.

**Dual token response** — the login endpoint returns both access token and refresh token in a single response, following standard OAuth2-inspired patterns.

**Secret key externalized** — the JWT signing key is loaded from `application.properties` via `@Value`, not hardcoded. This means the key is stable across restarts and can be managed per environment without changing code.

---

## Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security |
| Authentication | JWT (jjwt library) |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL |
| Build tool | Maven |
| API docs | Swagger / OpenAPI |

---

## Endpoints

### Authentication (public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/register` | Register a new user |
| POST | `/login` | Login and receive access + refresh token |
| POST | `/refresh` | Refresh an expired access token |

### User management (secured — requires Bearer token)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/utenti` | Get all active users (soft delete excluded) |
| GET | `/api/utenti/{id}` | Get a single user by ID |
| POST | `/api/utenti` | Create a new user record |
| PUT | `/api/utenti` | Update an existing user |
| DELETE | `/api/utenti/{id}` | Soft delete a user |

---

## How to run

### Prerequisites
- Java 17+
- MySQL running locally
- Maven

### Setup

1. Clone the repository
```bash
git clone https://github.com/your-username/spring_jwt_crud.git
cd spring_jwt_crud
```

2. Create the database using the provided SQL script
```bash
mysql -u root -p < src/main/java/sqlScripts.sql
```

3. Copy the example properties file and configure your environment
```bash
cp application.properties.example javaJwtCRUD/src/main/resources/application.properties
```

Then edit `application.properties` with your values:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
secretKey=your_base64_encoded_secret_key
```

> **Note:** `application.properties` is in `.gitignore` and never committed. Only `application.properties.example` is tracked. Never commit secret keys to version control.

4. Run the application
```bash
mvn spring-boot:run
```

5. Access Swagger UI at
```
http://localhost:8080/swagger-ui.html
```

---

## Example flow

1. Call `/register` with email and password to create a user
2. Call `/login` with the same credentials — receive access token and refresh token
3. Use the access token as `Bearer` header on all secured endpoints
4. When the access token expires, call `/refresh` with the refresh token to get a new one

---

## What I would improve next

- Add input validation with `@Valid` and proper error messages
- Add DTOs to separate internal entity from API response
- Add unit tests for JWT logic and integration tests for the auth flow
- Containerize with Docker
- Add Angular frontend connected to this backend

---

## Author

Lorenzo Santagata — Java Developer | Banking & Insurance Domain Expert

[LinkedIn](https://www.linkedin.com/in/lorenzo-santagata/)