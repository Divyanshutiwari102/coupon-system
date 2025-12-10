# Use Maven image
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy all project files
COPY . .

# Run the app directly using Maven
# This bypasses the "repackage" error completely
CMD ["mvn", "spring-boot:run"]