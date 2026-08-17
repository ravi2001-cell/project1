Project1 - Java 3-tier scaffold

This repo contains a Maven multi-module Java project (Java 21) with:
- common: shared DTOs
- api: Spring Boot REST API (connects to PostgreSQL, uses Flyway for migrations)
- web: Spring Boot + Thymeleaf frontend that calls the API

Quick start (run locally without Docker):
1. Ensure Java 21 and Maven are installed.
2. Start a PostgreSQL instance (default expected at jdbc:postgresql://localhost:5432/project1 with user/password postgres/postgres). You can use Docker:

   docker run --name project1-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=project1 -p 5432:5432 -d postgres:15

3. Build and run the api and web modules from the repository root:

   mvn -T 1C -pl api -am package spring-boot:run
   mvn -T 1C -pl web -am package spring-boot:run

   (api will listen on port 8081; web on 8080)

4. Open http://localhost:8080 to see the user list (data seeded by Flyway).

Build Docker images (optional):
  docker build -f api/Dockerfile -t project1-api:latest .
  docker build -f web/Dockerfile -t project1-web:latest .

Notes:
- The API expects a PostgreSQL database. You can configure connection using environment variables:
  SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD
- I kept the scaffold minimal so you can extend it. Tell me if you want GitHub Actions, Kubernetes manifests, Helm chart, or extra features (health checks, metrics, CI/CD).
