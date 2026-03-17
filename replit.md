# Sistema de Cadastro de Eventos

A Java console application for registering and managing city events.

## Architecture

- **Language**: Java 17 (GraalVM 22.3.1)
- **Build tool**: Maven 3
- **Persistence**: Plain text files (`events.data`, `users.data`)

## Project Structure

```
src/main/java/com/eventos/
├── Main.java           - Entry point, console UI menus
├── SistemaEventos.java - Business logic layer
├── Evento.java         - Event entity
├── Usuario.java        - User entity
├── Categoria.java      - Event categories enum
└── Persistencia.java   - File-based persistence
```

## Running the Application

The workflow `Start application` builds and runs the JAR automatically:
```bash
mvn package -q && java -jar target/sistema-eventos.jar
```

## Features

- Register users (name, email, phone)
- Register events (name, address, category, time, description)
- Pre-defined categories: Festa, Esportivo, Show, Cultural, Educacional, Gastronômico, Outro
- List all events, future events, past events, events by category
- Confirm and cancel event participation
- Events sorted by `LocalDateTime`
- Data persisted to `events.data` and `users.data` on every change

## Data Files

- `events.data` - Pipe-delimited event records, loaded on startup
- `users.data` - Pipe-delimited user records, loaded on startup
