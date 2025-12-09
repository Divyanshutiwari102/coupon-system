# Stage 1: Build the JAR file
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# We use -DskipTests to save time and avoid test errors
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR file
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# We copy the JAR file. We use a wildcard (*) so it works even if the version name changes.
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]