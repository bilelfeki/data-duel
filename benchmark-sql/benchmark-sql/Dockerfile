# Stage 1: Build the application
FROM maven:3.9.9-sapmachine-22 AS build

# Copy the application source code to the container
COPY . /app/benchmark-sql


# Build the application
RUN mvn -f /app/benchmark-sql/pom.xml clean install -DskipTests


# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Copy the built jar from the build stage
#COPY --from=build /app/benchmark-sql/target/benchmark-sql-0.0.1-SNAPSHOT.jar /app/benchmark-sql-0.0.1-SNAPSHOT.jar

COPY --from=build /app/benchmark-sql/target/*.jar /app/benchmark-sql.jar

# Set the entry point to run the jar

ENTRYPOINT ["java", "-jar", "/app/benchmark-sql.jar"]
