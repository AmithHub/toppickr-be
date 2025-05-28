# Use a base image with OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml first (for dependencies)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this will cache dependencies if unchanged)
RUN ./mvnw dependency:go-offline

# Copy the rest of the application code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port the app will run on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/toppickr-0.0.1-SNAPSHOT.jar"]
