FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

# Copy files
COPY . .

# 1. Compile the Java code manually
# 2. Download all dependencies to a specific folder
RUN mvn clean compile dependency:copy-dependencies -DoutputDirectory=target/dependency

# Run the app using raw Java (Bypassing Maven Plugins entirely)
# "target/classes" contains your code
# "target/dependency/*" contains Spring Boot libraries
CMD ["java", "-cp", "target/classes:target/dependency/*", "com.anshumat.CouponApplication"]