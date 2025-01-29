# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the packaged WAR file from the builder stage
COPY --from=builder /app/target/socket-0.0.1-SNAPSHOT.war /app/socket-0.0.1-SNAPSHOT.war

# Expose the port your application will run on (default for Spring Boot is 8080)
EXPOSE 8081 8080

# Specify the command to run the application
CMD ["java", "-jar", "/app/socket-0.0.1-SNAPSHOT.war"]
