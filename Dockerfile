# Stage 1: Build the application
FROM maven:3.8.6-openjdk AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the packaged WAR file from the builder stage
COPY --from=builder /app/target/socket-0.0.1-SNAPSHOT.war /app/socket-0.0.1-SNAPSHOT.war

# Specify the command to run the application
CMD ["java", "-jar", "/app/socket-0.0.1-SNAPSHOT.war"]
