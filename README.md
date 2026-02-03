# Spring Boot User Management API

Enterprise-ready **User Management REST API** built with Spring Boot.
This project demonstrates **professional backend engineering practices** including clean architecture, standardized API responses, validation, pagination, advanced search, soft delete, and comprehensive testing.

> 🎯 **Purpose**: Portfolio project for Upwork & freelance work to showcase enterprise-level Spring Boot backend development.

---

## 🚀 Key Features

### ✅ Core Features

* Full CRUD User Management (Create, Read, Update, Delete)
* Pagination & sorting support
* Advanced search & filtering endpoints
* DTO-based request & response (no entity exposure)
* Transaction-safe service layer

### 🧱 Enterprise Architecture

* Clean layered architecture (Controller → Service → Repository)
* Centralized global exception handling
* Standardized API response & error schema
* Validation with meaningful error messages

### 🗑 Soft Delete

* Soft delete support (logical delete)

### 📘 API Documentation

* Swagger / OpenAPI documentation (Springdoc)
* Detailed request, response, and error schemas
* Interactive API testing via Swagger UI

### 🧪 Testing & Quality

* Unit tests for service layer
* Controller tests using MockMvc

---

## 🛠 Tech Stack

* **Java 17**
* **Spring Boot 3**
* Spring Web
* Spring Data JPA (Hibernate)
* Spring Validation (Jakarta Validation)
* PostgreSQL (production-like setup)
* Springdoc OpenAPI (Swagger UI)
* Lombok
* Maven
* JUnit 5, Mockito, MockMvc

---

## 📁 Project Structure

```
com.bintang.usermanagement
├── controller        # REST API controllers
├── service           # Business logic
│   └── impl
├── repository        # Data access layer
├── entity            # JPA entities
├── dto               # Request & response objects
│   ├── request
│   └── response
├── exception         # Global exception handling
├── config            # Swagger
├── specification     # Search with specification
└── SpringBootUserManagementApplication.java
```

---

## 📮 API Overview

### ➕ Create User

`POST /api/users`

### 📄 Get Users (Pagination & Search)

`GET /api/users?page=0&size=10&sort=id,asc`

### 🔍 Get User by ID

`GET /api/users/{id}`

### ✏️ Update User

`PUT /api/users/{id}`

### 🗑 Soft Delete User

`DELETE /api/users/{id}`

---

## 📦 Standard API Response

### ✅ Success Response

```json
{
  "status": "SUCCESS",
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "John",
    "email": "john@mail.com"
  },
  "timestamp": "2026-02-02T14:30:12"
}
```

### ❌ Error Response

```json
{
  "status": "ERROR",
  "message": "Validation failed",
  "errors": {
    "email": "must be a well-formed email address"
  },
  "timestamp": "2026-02-02T14:30:12"
}
```

---

## 📘 Swagger API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

Swagger includes:

* All endpoints
* Request & response schemas
* Error response documentation
* Pagination & filter examples

---

## ⚙️ Environment Configuration

This project follows **enterprise best practices** by externalizing sensitive configuration.

### Required Environment Variables

```
DB_USERNAME
DB_PASSWORD
```

### Optional

```
DB_URL (default: jdbc:postgresql://localhost:5432/user_management_db)
```

Example (Mac / Linux):

```bash
export DB_USERNAME=your_db_username
export DB_PASSWORD=your_db_password
```

Example (Windows PowerShell):

```powershell
setx DB_USERNAME your_db_username
setx DB_PASSWORD your_db_password
```

---

## ▶️ How to Run Locally

### 1️⃣ Create PostgreSQL database

```sql
CREATE DATABASE user_management_db;
```

### 2️⃣ Run application

```bash
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

## 🧪 Testing & Coverage

* Service layer unit tests using Mockito
* Controller tests using MockMvc

Generate coverage report:

```bash
mvn test
```

---

## 🧠 Design Principles

* Consistent API contract
* Separation of concerns
* Production-oriented configuration
* Easy to extend with:

    * JWT Authentication
    * Role-based authorization
    * Docker & CI/CD pipelines
    * API Gateway integration

---

## 👤 Author

**Bintang Mada**
Java Spring Boot Backend Developer

> This repository is designed as a professional backend portfolio demonstrating enterprise-ready Spring Boot API development suitable for freelance and long-term projects.
