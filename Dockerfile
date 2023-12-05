# Use a base image with Java 11 installed
FROM openjdk:21-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the Spring Boot application JAR file to the container
COPY target/asset_reference-0.0.1-SNAPSHOT.jar /app

# Expose the port on which the Spring Boot application is running
#EXPOSE 8080

# Set the entry point for the Docker container to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "asset_reference-0.0.1-SNAPSHOT.jar"]