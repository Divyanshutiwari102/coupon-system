# Use Maven image
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy all project files
COPY . .

# RUN the app directly from source.
# This compiles and starts the app in one step, bypassing the JAR error.
CMD ["mvn", "spring-boot:run"]