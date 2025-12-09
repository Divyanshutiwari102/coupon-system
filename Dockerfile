# Use a single stage with Maven pre-installed
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy all files
COPY . .

# Run the app directly using Maven (Bypasses JAR packaging issues)
CMD ["mvn", "spring-boot:run"]