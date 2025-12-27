# Technical Documentation – Clean Architecture Project

## 📌 Overview

This project follows **Clean Architecture** principles, ensuring separation of concerns, high testability, scalability, and maintainability. It is composed of four main layers:

1. **Core** – Business logic (Entities, Domain Services, Repositories, Models)
2. **Application** – Use cases and orchestration of business rules
3. **Infrastructure** – Framework-specific implementations and integrations
4. **UI** – Interface layer (REST APIs, CLI, gRPC, etc.)

---

## 🏗 Architecture Breakdown

### 1. **Core Layer** (High-level business logic)

Responsible for domain logic and state transformation. Contains:

* **Entities**: Rich domain models (e.g., `User`, `Task`, `Notification`, `Technology`, etc.)
* **Models**: Lightweight data structures used internally for transformations or read models
* **Repositories** (Interfaces): Abstract access to data stores to apply Dependency Inversion Principle
* **Services**: Stateless domain services that don't belong to any single entity
* **Factory**: Encapsulates the creation logic for complex domain objects or aggregates, helping to enforce invariants and encapsulate construction rules.
* 
#### 🔍 Example Structure:

```
Core/
├── User/
│   ├── Entity/           → User.java
│   ├── model/            → LoginUserModel.java, UserModel.java
│   ├── Service/          → UserService.java, PasswordEncryptor.java
│   └── UserRepository.java (Interface)
```

---

### 2. **Application Layer** (Orchestration of use cases)

This layer contains **Use Cases** for each domain. Each use case encapsulates a specific business operation (e.g., registering or logging in a user).

* Each use case contains logic that coordinates calls to the domain layer.
* Uses **Commands/Result** or DTOs for inputs and outputs.

#### 🔍 Example Structure:

```
Application/
└── User/
    ├── LoginAccount/
    │   ├── LoginAccountUseCase.java
    │   └── LoginAccountCommand.java
    └── RegisterAccount/
        └── RegisterAccountUseCase.java
        └── RegisterAccountCommand.java

```

---

### 3. **Infrastructure Layer** (Technology-specific code)

Holds code that interacts with frameworks, libraries, and external systems. It contains:

* **Security** (JWT, Spring Security configs)
* **Exception Handling**
* **Authentication**

🚫 Business logic must **never** be implemented here.

#### 🔍 Example Structure:

```
infrastructure/
├── Exception/              → GlobalException.java, UserAuthenticationException.java
├── Jwt/                    → JwtGeneration.java, JwtExtracting.java, JwtFilter.java
└── Security/               → SecurityConfig.java
```

---

### 4. **UI Layer** (Interface to the outside world)

This layer exposes the application's functionality via REST APIs (or CLI, gRPC). It translates HTTP requests into **use case calls**,
and use case results into HTTP responses.

* **Controllers**: Handle incoming HTTP requests
* **Resources**: Organize input/output DTOs for request/response

#### 🔍 Example Structure:

```
UI/
└── Rest/
    └── User/
        └── Auth/
            ├── Login/
            │ ├── LoginAccountController.java
            │ ├── LoginAccountRequest.java
            │ └── LoginAccountResponse.java
            ├── Register/
            │ ├── RegisterAccountController.java
            │ ├── RegisterAccountRequest.java
    └── Resources/
    └── UserResources.java

```

---

## 🔄 Flow of Control (Example – Login Flow)

1. **UI (REST Controller)** receives `LoginAccountRequest`
2. Converts request to DTO/command and calls `LoginAccountUseCase`
3. **Application Layer** orchestrates domain logic
4. **Core Layer** authenticates user via `UserService` and `UserRepository`
5. **Infrastructure Layer** implements `UserRepository` and security logic (e.g., JWT)
6. Response returned to the Controller → `LoginAccountResponse`

---

## ✅ Principles Followed

* **Separation of concerns** between layers
* **Dependency Inversion**: Core depends only on abstractions
* **Single Responsibility**: Each class/module has one reason to change
* **Testability**: Domain and application logic is easily testable
* **Framework Independence**: Business logic can be reused with different UIs or frameworks

---

## 📦 Project Root Overview

```
.
├── Dockerfile-backend
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/spring/techpractica/
                ├── TechPracticaApplication.java
                ├── Application/
                ├── Core/
                ├── infrastructure/
                └── UI/
