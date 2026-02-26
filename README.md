# Task Management Demo API

A simple RESTful API for managing tasks, built with Java and Spring Boot.

## 🚀 Features

- Create new tasks
- Retrieve a list of all tasks
- Get specific task details by ID
- Update existing tasks
- Delete tasks
- Global exception handling
- OpenAPI / Swagger documentation support

## 🛠️ Technologies

- **Java 17**
- **Spring Boot** (Web, Data JPA)
- **Maven**
- **Lombok**
- **OpenAPI / Swagger** (for API documentation)

## ⚙️ Configuration

The application requires a database connection. You must configure the following environment variables before running the application:

| Variable | Description | Example |
|----------|-------------|---------|
| `DATASOURCE_URL` | JDBC URL for the database | `jdbc:postgresql://localhost:5432/tasks_db` |
| `DATASOURCE_USERNAME` | Database username | `postgres` |
| `DATASOURCE_PASSWORD` | Database password | `secret` |

Alternatively, you can set these directly in `src/main/resources/application.properties` for local development.

## 🏃‍♂️ How to Run

### Prerequisites
- JDK 17 or higher installed
- Maven installed (or use the provided `mvnw` wrapper)

### Build and Run

1. **Clone the repository**
2. **Build the project**:
   ```bash
   ./mvnw clean install
   ```
3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   *Make sure to set the environment variables mentioned above.*

## 📚 API Endpoints

The base URL for the API is `/api/tasks`.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `POST` | `/api/tasks` | Create a new task |
| `PATCH` | `/api/tasks/{id}` | Update an existing task |
| `DELETE` | `/api/tasks/{id}` | Delete a task |

### Example Task Object
```json
{
  "title": "Finish Project",
  "description": "Write the README file",
  "status": "NEW"
}
```

## 📂 Project Structure

The project follows a layered architecture:

- **infrastructure**: Common utilities, base entities, and global exception handling.
- **tasks/businesslayer**:
    - `controllers`: REST API endpoints.
    - `services`: Business logic.
    - `dtos`: Data Transfer Objects for requests/responses.
    - `mappers`: Object mapping.
- **tasks/datalayer**: 
    - `entities`: Database entities.
    - `repositories`: Data access capabilities.

