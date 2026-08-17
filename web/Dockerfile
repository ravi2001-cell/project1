# Use multi-stage build
FROM maven:3.10.1-jdk-21-slim AS build
WORKDIR /workspace
COPY pom.xml ./
COPY common/pom.xml ./common/pom.xml
COPY web/pom.xml ./web/pom.xml
COPY common/src ./common/src
COPY web/src ./web/src
RUN mvn -f pom.xml -DskipTests package -pl web -am -T 1C

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/web/target/web-0.1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
