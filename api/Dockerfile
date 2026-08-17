# syntax = docker/dockerfile:1.4
# BuildKit-optimized multi-stage Dockerfile for the `api` module
# - copies POMs first to leverage layer caching
# - uses BuildKit cache mount for Maven repository
# - builds only the api module in a multi-module repo

FROM maven:3.10.1-jdk-21-slim AS build
WORKDIR /workspace

# Copy only POMs first so dependency layer can be cached
COPY pom.xml ./
COPY common/pom.xml ./common/pom.xml
COPY api/pom.xml ./api/pom.xml
COPY web/pom.xml ./web/pom.xml

# Use BuildKit cache for Maven local repo to speed up dependency downloads
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests dependency:go-offline

# Copy sources after dependency prefetch
COPY common/src ./common/src
COPY api/src ./api/src

# Build only the api module and its required modules
RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests package -pl api -am

# Runtime image (small JRE)
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

# Copy the built artifact from the builder stage
COPY --from=build /workspace/api/target/*.jar ./app.jar

# Create non-root user and fix permissions
RUN groupadd --system app && useradd --system --gid app app \
    && chown app:app /app/app.jar

USER app

# Expose API port
EXPOSE 8081

# Tunable JVM options (override at runtime)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Use exec form so PID 1 receives signals; allow $JAVA_OPTS expansion
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
