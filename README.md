# Points Demo Application
This repository contains a simple Java application that demonstrates a basic points management domain. 
It is implemented using Spring Boot and includes domain models, repositories, and unit/integration tests. 
Currently, there is no main application or REST interface implemented - only the core domain logic and tests 
are available at present.

---

# Overview
The application is designed to manage "points" for accounts. It supports:

- **Account Creation:**  
  Accounts have a name and a balance. The default balance is 0 unless otherwise specified.

- **Spending Points:**  
  A service method allows points to be spent from an account, with validations to prevent spending more than the 
  available balance. An exception (`InsufficientPointsException`) is thrown if an attempt is made to spend too 
  many points.

- **Idempotency:**  
  There is a stub for idempotency keys handling (an `IdempotencyKeyRepository` is present but commented out in 
  the tests). This suggests that future work may include ensuring operations are idempotent.

The provided unit tests in `PointsManagementIntegrationTests` cover:
- Spending points under valid conditions.
- Handling an attempt to spend more points than available.
- Basic CRUD operations on the `Accounts` entity.

---

## Technologies and Frameworks
- **Java 8+**
- **Spring Boot**
- **Spring Data JPA**
- **JUnit 5 (Jupiter)**
- **MySQL (or compatible) for persistence**  
  The provided DDL is compatible with MySQL using InnoDB.

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- **Maven** for dependency management and building the project.
- **MySQL Database:**  
  Use the provided DDL to create the necessary tables. Alternatively, you can configure an in-memory 
  database for testing purposes.

### Database Schema

The application uses two tables: `accounts` and `idempotency_keys`.

**DDL for `accounts`:**

```sql
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `balance` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

**DDL for `idempotency_keys`:**

```sql
CREATE TABLE `idempotency_keys` (
  `IDMP_ID` bigint NOT NULL AUTO_INCREMENT,
  `idempotency_key` varchar(255) NOT NULL,
  `account_name` varchar(255) NOT NULL,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IDMP_ID`),
  UNIQUE KEY `idempotency_key` (`idempotency_key`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

Ensure these tables are available in your database, and update the application's database configuration as needed.

---

## Configuration

This application uses Spring Boot’s externalized configuration to manage sensitive data. Sensitive information, such as database credentials, should not be hard-coded in the source code. Instead, they are injected using environment variables.

### Setting Up Environment Variables

For example, the database username and password are configured via the `POINTS_DB_USERNAME` and `POINTS_DB_PASSWORD` environment variables. In your `application.properties` file, you'll find the following configuration:

```properties
spring.datasource.password=${POINTS_DB_USERNAME:defaultUsername}
spring.datasource.password=${POINTS_DB_PASSWORD:defaultPassword}
```

- **POINTS_DB_USERNAME:**  
  Set this environment variable to the actual database username when running the application.  
  *Example (Linux/Mac):*
  ```bash
  export POINTS_DB_USERNAME=your_secure_db_username
  ```
  *Example (Windows CMD):*
  ```cmd
  set POINTS_DB_USERNAME=your_secure_db_username
  ```

  - **Default Value:**  
  A default value (`defaultUsername`) is provided for development purposes. It is strongly recommended to override this in production.

- **POINTS_DB_PASSWORD:**  
  Set this environment variable to the actual database password when running the application.  
  *Example (Linux/Mac):*
  ```bash
  export POINTS_DB_PASSWORD=your_secure_db_password
  ```
  *Example (Windows CMD):*
  ```cmd
  set POINTS_DB_PASSWORD=your_secure_db_password
  ```

  - **Default Value:**  
  A default value (`defaultPassword`) is provided for development purposes. It is strongly recommended to override this in production.

### Running the Application

Make sure to set the necessary environment variables before starting the application. For example:

```bash
export DB_PASSWORD=your_secure_db_password
mvn spring-boot:run
```

Alternatively, if you are using Docker, you can pass the environment variable via your Docker configuration.

---

## Running the Tests

The project includes integration tests located in `PointsManagementIntegrationTests`. These tests:
- Set up two accounts (one with an initial balance and one with a zero balance).
- Validate that spending points behaves as expected (both valid spending and attempts to overspend).
- Clean up the test data after each test run.

To run the tests, execute:

```bash
mvn test
```

This command will compile the project and run the tests using Maven.

---

## Future Enhancements

- **Main Application & REST Interface:**  
  A REST interface will be added to expose endpoints for account management and points operations.

- **Idempotency Handling:**  
  Further work on idempotency key management will ensure that duplicate operations are handled gracefully.

- **Enhanced Business Logic:**  
  Additional business rules and error handling may be implemented as the application evolves.

- **Integration with External Systems:**  
  Consider integration with messaging systems or other services as needed.

---

## Contributing

Contributions are welcome! Feel free to fork this repository and submit pull requests. Please follow standard 
GitHub practices for issue tracking and code reviews.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

This README provides an overview of the current state of the Points Demo application and outlines how to set 
it up, run tests, and contribute. Enjoy exploring and extending the project!