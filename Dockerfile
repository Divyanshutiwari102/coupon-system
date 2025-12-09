# Use Maven image
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy all files
COPY . .

# Run the app directly using Maven (Bypasses JAR packaging issues)
# This compiles the code and starts it immediately
CMD ["mvn", "spring-boot:run"]