# 🧼 Clean Architecture Development Guideline

This document defines the folder structure, responsibilities, and principles for developing software using **Clean Architecture**. It helps ensure separation of concerns, testability, and maintainability.

---

## 📁 Folder Structure Overview

```plaintext
/src
│
├── Core/                          # Business domain logic (pure)
│   └── <DomainName>/             # e.g., User, Vehicle, Task
│       ├── Entity/               # Core business objects (with identity)
│       ├── Model/                # Value objects, enums, etc.
│       ├── Repository/           # Interfaces only (DI targets)
│       ├── Service/              # Domain logic not tied to an entity
│       └── Factory/              # Complex entity construction
│
├── Application/                  # Use cases (application-specific logic)
│   └── <DomainName>/
│       ├── UseCase/              # Actions like CreateUser, GetVehicle
│       └── DTO                 # Input/output data formats
│       └── Events       
├── Infrastructure/              # Technical implementation details
│   ├── Persistence/              # DB, ORM, repository implementations
│   └── Config/                   # Dependency injection, environment
│     └── Secuirty/ 
├── Presentation/                # Interface adapters (HTTP, CLI, etc.)
│   ├── Controllers/             # REST, GraphQL handlers
|       ├── /<DomainController>
|           ├── /<Request>              
│   ├── Resources /               # Response objects for frontend
```
# Core Layer (Domain)
The Core layer contains all business rules and domain logic. It is framework-agnostic and should not depend on any external libraries.

#Application Layer (Use Cases)
The Application layer coordinates domain logic to fulfill specific user interactions. It orchestrates operations but contains no technical code.

#Infrastructure Layer
The Infrastructure layer includes concrete implementations of interfaces declared in the Core and Application layers. It interacts with the outside world.

#Presentation Layer (Delivery / Interface)
The Presentation layer handles user interactions, including HTTP, CLI, GraphQL, or UI. It should only communicate with the Application layer.

