# Use Maven image
FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy all files
COPY . .

# Run directly using Maven.
# We explicitly force the Main Class location to avoid confusion.
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.main-class=com.anshumat.CouponApplication"]