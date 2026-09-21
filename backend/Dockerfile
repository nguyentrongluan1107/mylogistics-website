FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/vtp-cms-api-0.1.0.jar app.jar
USER 10001
ENTRYPOINT ["java","-jar","app.jar"]
