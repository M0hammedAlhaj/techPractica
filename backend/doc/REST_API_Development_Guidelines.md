
# 🧭 REST API Architecture Guide

This document explains how to structure, develop, and maintain a RESTful API using a clear, modular, and standardized approach.

---

## 📦 Project Structure

```
src/main/java/com/example/api/
├── rest/
│   ├── user/
│   │   ├── auth/
│   │   │   ├── LoginAccount/
│   │   │   │   ├── LoginController.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── LoginResponse.java
│
├── resources/
│   ├── user/
│   │   ├── UserResources/
│   │   ├── UserCollection/
```

### 💡 Structure Philosophy

- **Domain-based folders**: Each domain (e.g., `user`, `auth`) contains multiple endpoints grouped in folders.
- **Endpoint Isolation**: Each endpoint (e.g., `LoginAccount`) contains its own controller, request, and response.
- **Resources Layer**: Instead of exposing domain entities directly, we expose "resources" that act as DTOs — filtering sensitive fields and breaking circular dependencies.
- **Versioned Endpoints**: Endpoint names or URIs can include versions (e.g., `/v1/auth/login`) to make production switching safer and simpler.

---

## ✅ Standardized API Responses

We use a **consistent response format** across all APIs for both **success** and **error** cases. This makes it easier for frontend developers and consumers to work with the API reliably.

---

### 🔹 `StandardSuccessResponse<T>`

```java
@Builder
public record StandardSuccessResponse<T>(
    T data,
    int status,
    String message
) {}
```

#### 📌 Explanation:

- `data`: The actual response payload (generic for flexibility).
- `status`: HTTP status code (e.g., 200, 201).
- `message`: A human-readable success message.

✅ This ensures all success responses are **structured and consistent**, making it easier to handle on the client side.

---

### 🔻 `StandardErrorResponse`

```java
@Builder
public record StandardErrorResponse(
    Instant timestamp,
    int status,
    String message,
    String code
) {}
```

#### 📌 Explanation:

- `timestamp`: When the error occurred (for logs or client feedback).
- `status`: HTTP status code (e.g., 400, 404, 500).
- `message`: A human-readable error message.
- `code`: A custom error code (e.g., `AUTH_INVALID_CREDENTIALS`) for easier debugging and internationalization.

🚫 This structure makes error handling **predictable and useful**, especially for clients that depend on programmatic feedback.

---

## 🚨 Error Handling


In this architecture, we support two levels of exception handling to balance flexibility and maintainability:

### 2.🛡️ Global Error Handler Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<StandardErrorResponse> handleCustomError(CustomBusinessException ex) {
        return ResponseEntity
            .status(ex.getStatus())
            .body(StandardErrorResponse.builder()
                .timestamp(Instant.now())
                .status(ex.getStatus())
                .message(ex.getMessage())
                .code(ex.getCode())
                .build());
    }

    // more handlers...
}
```
-  With this, each endpoint doesn’t need to worry about error handling — everything is centralized.
-  Helps identify **which error** is relevant to **which endpoint** through exception types and codes.
-  Apply DRY (don't repeat yourself) principle.
### 1. 🔄 Traditional (Local) Exception Handling

Used directly within the controller or service layer using `try/catch`.

### ✅ When to use:
- You want to preserve context specific to the current request or endpoint
- You're handling expected business exceptions (e.g., invalid login attempts, validation failures)
- Helps developers avoid forgetting important details for that specific endpoint

### 🧩 Example:

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        var result = authService.login(request);
        return ResponseEntity.ok(StandardSuccessResponse.builder()
            .data(result)
            .status(200)
            .message("Login successful")
            .build());
    } catch (InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(StandardErrorResponse.builder()
                .timestamp(Instant.now())
                .status(401)
                .message("Invalid email or password")
                .code("AUTH_INVALID_CREDENTIALS")
                .build());
    }
}
```



---

## 🛠️ Development Guidelines

- ✅ Every new endpoint should:
    - Be placed inside its feature folder (`/LoginAccount/`, etc.)
    - Define its own `Request` and `Response` classes
    - Return `StandardSuccessResponse` or throw exceptions handled by global error handler
- 🧪 Use DTOs (resource classes) to **protect internal domain models**
- 🧩 Use **API versioning** where needed (`/v1/`, `/v2/`, etc.)

---

## 📘 Example Endpoint Flow: Login

```http
POST /v1/user/auth/login
```

**Request:**
```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

**Success Response:**
```json
{
  "data": {
    "token": "JWT...",
    "user": { "...": "..." }
  },
  "status": 200,
  "message": "Login successful"
}
```

**Error Response (Invalid Credentials):**
```json
{
  "timestamp": "2025-08-08T20:00:00Z",
  "status": 401,
  "message": "Invalid email or password",
  "code": "AUTH_INVALID_CREDENTIALS"
}
```