# Comparus Test Task

Spring Boot app for querying multiple databases (PostgreSQL + MySQL).

## Prerequisites

- Java 21+
- Maven
- Docker & Docker Compose

## Run Databases

```bash
    docker-compose up -d
```

SQL init files:

- `./docker/db/init_db1.sql` → PostgreSQL DB-1 (`users`)
- `./docker/db/init_db2.sql` → PostgreSQL DB-2 (`customers`)
- `./docker/db/init_db3.sql` → MySQL DB-3 (`tusers`)

## Spring Boot Configuration

Located in `application.yml`:

## Run Spring Boot Application

```bash
    mvn spring-boot:run
```