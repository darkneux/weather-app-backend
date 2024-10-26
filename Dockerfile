# Use the latest Java 22 JDK slim image
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and configuration files
COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
COPY ./.mvn ./.mvn

# Resolve project dependencies; this layer will be cached until pom.xml changes
RUN chmod +x mvnw && ./mvnw dependency:resolve

# Copy the project source code
COPY ./src ./src

# Build the application, skipping tests
RUN ./mvnw package -DskipTests

# Copy the built JAR file to the /app directory
COPY target/*.jar /app/weathermonitoring.jar

# Expose the application port
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/weathermonitoring.jar"]
