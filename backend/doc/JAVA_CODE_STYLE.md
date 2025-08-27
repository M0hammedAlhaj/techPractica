# Java Code Style Guide

This guide defines coding standards for Java projects to ensure consistency, readability, and maintainability.

---

## 1. File Structure

* **Package declaration** on top.
* **Imports** grouped in the following order:

  1. Standard library
  2. Third-party libraries
  3. Project-specific
* **No wildcard imports** (`*`).
* **One top-level class per file**.

**Example:**

```java
package com.example.account.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.UI.Rest.Resources.User.UserResources;
```

---

## 2. Code Structure

Class layout should follow this order:

1. Import statements
2. Class-level documentation comment
3. Class or interface declaration
4. Static variables
5. Instance variables
6. Constructors
7. Methods (including business logic)

**Example:**

```java
@RestController
public class AccountController {

    private final JwtExtracting jwtExtracting;
    private final UseCaseExecutor useCase;

    public AccountController(JwtExtracting jwtExtracting, UseCaseExecutor useCase) {
        this.jwtExtracting = jwtExtracting;
        this.useCase = useCase;
    }

    @GetMapping("/active-account")
    public ResponseEntity<StandardSuccessResponse> verifyToken(@RequestParam String token) {
        UUID id = jwtExtracting.extractId(token);
        User user = useCase.execute(new ActiveAccountCommand(id));

        StandardSuccessResponse response = StandardSuccessResponse.builder()
                .data(new UserResource(user))
                .message("Active account has been verified")
                .status(HttpStatus.ACCEPTED.value())
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
```

---

## 3. Method Structure

* Prefer **short methods** (<30 lines).
* Extract complex builders into local variables.
* Use **blank lines to separate logical steps** inside methods.
* **Do not add a blank line after method signatures**.
* Avoid unnecessary blank lines within the method.

---

## 4. Naming Conventions

* **Packages:** lowercase, singular (e.g., `com.example.account`)
* **Classes & Interfaces:** PascalCase (e.g., `AccountController`)
* **Constants:** UPPER\_SNAKE\_CASE (e.g., `MAX_COUNT`)

---

## 5. Blank Lines

* One blank line between fields, constructors, and methods.
* One blank line between logical blocks inside a method.
* **No multiple blank lines in a row.**
* **No blank lines at the start or end** of methods.
