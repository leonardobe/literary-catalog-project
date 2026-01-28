# 📚 Literary Catalog

![Java](https://img.shields.io/badge/Java-21-red) ![Spring
Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Backend](https://img.shields.io/badge/Backend-Java-success)
![Console](https://img.shields.io/badge/Application-Console-lightgrey)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
![License](https://img.shields.io/badge/License-MIT-green)

**Literary Catalog** is a **console-based Java application** built with
**Spring Boot** that consumes the **Gutendex public API** to search,
retrieve, and persist book data into a PostgreSQL database.

This project was developed for **learning and portfolio purposes**,
focusing on **clean architecture**, proper **domain modeling**, API
consumption, data persistence, and separation of concerns.

---

## 🚀 Features

### 📡 Gutendex API Integration

-   🔍 Search books by title
-   👤 Search books by author
-   🔎 Search books by title and author
-   🔥 List top 10 most downloaded books

### 💾 Database Persistence (PostgreSQL)

-   📚 Save selected books from the API
-   👥 Persist multiple authors per book (Many-to-Many relationship)
-   🧠 Avoid duplicate books using Gutendex ID
-   📖 List saved books
-   ✍️ List saved authors
-   🌍 Filter books by language

### 🧩 Architectural Highlights

-   DTO-based API mapping
-   Dedicated mapper layer
-   Clean separation between API client, services, and persistence
-   Domain-driven relationship handling with utility methods
-   Console-driven interaction (no REST endpoints)

---

## 🛠️ Tech Stack

-   Java 21
-   Spring Boot 3
-   Spring Data JPA
-   Hibernate
-   Spring WebFlux (WebClient)
-   PostgreSQL
-   Maven

---

## 📁 Project Structure

``` text
com.br.literarycatalog
├── client
│   └── LiteraryCatalogClient       # Responsible for consuming the Gutendex public API
├── config
│   └── WebClientConfig             # WebClient configuration for API integration
├── runner
│   ├── LiteraryCatalogRunner       # Application entry point using CommandLineRunner
│   └── Menu                        # Console menu and user interaction
├── console
│   ├── ApiResultHandle             # User interaction
│   └── ConsoleUI                   # Console formatting
├── controller
│   └── LiteraryCatalogController   # Orchestrates console actions and service calls
├── domain
│   └── entity
│       ├── Book                    # JPA entity representing books
│       └── Author                  # JPA entity representing authors
├── dto
│   └── api
│       ├── GutendexDTO             # Root API response mapping
├── mapper
│   └── BookMapper                  # Maps API DTOs to domain entities
├── repository
│   ├── BookRepository              # JPA repository for Book entity
│   └── AuthorRepository            # JPA repository for Author entity
├── service
│   ├── GutendexService             # Handles API-related business logic
│   └── BookService                 # Handles persistence and domain rules
└── GutendexLibraryApplication
```

---

## 🔗 Domain Model

-   A book may have multiple authors
-   An author may have multiple books
-   Bidirectional Many-to-Many relationship
-   Utility methods ensure domain consistency

---

## ⚙️ Configuration (application.yml)

The project uses **YAML** configuration.
You must provide your own database credentials.

```code
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/literary_catalog
    username: YOUR_DB_USERNAME
    password: YOUR_DB_PASSWORD
```
--- 

## ▶️ Running the Project

### Prerequisites

-   Java 17+
-   Maven
-   PostgreSQL

### Clone the repository

``` bash
git clone https://github.com/leonardobe/literary-catalog-project.git
```

### Create database

``` sql
CREATE DATABASE literary_catalog;
```

### Configure application.yml
Set your PostgreSQL credentials.

### Run the application

``` bash
mvn spring-boot:run
```

---

## 🧭 Console Menu Example

``` text
1 - Search book by title (API)
2 - Search book by author (API)
3 - Search book by title and author (API)
4 - List top 10 most downloaded books (API)
5 - List saved books (Database)
6 - List saved authors (Database)
7 - List books by language (Database)
0 - Exit
```

---

📈 Learning Outcomes

This project reinforces key Java backend development concepts, including:

- External API consumption using Spring WebClient
- Layered architecture and clear separation of concerns
- DTO-based JSON mapping with Jackson
- JPQL queries and relational modeling with JPA
- Proper Many-to-Many domain modeling and relationship consistency
- Domain-driven validation using entity utility methods
- Console applications built with production-level backend architecture
- Clean, maintainable, and scalable Java & Spring Boot design

---

## 📄 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Author

Developed by **Leonardo Curtis**.
Focused on Java back-end development, clean architecture, and continuous learning.
