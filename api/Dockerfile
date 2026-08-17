# Use multi-stage build to produce a small runtime image
FROM maven:3.10.1-jdk-21-slim AS build
WORKDIR /workspace
COPY pom.xml ./
COPY common/pom.xml ./common/pom.xml
COPY api/pom.xml ./api/pom.xml
# copy only sources needed for dependency resolution
COPY common/src ./common/src
COPY api/src ./api/src
RUN mvn -f pom.xml -DskipTests package -pl api -am -T 1C

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/api/target/api-0.1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
