# Use Maven image to run directly
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy everything
COPY . .

# Run the application directly using Maven
# This is slower to start but guaranteed to find your Main class if it exists
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=default"]