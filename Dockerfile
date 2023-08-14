# Build step
FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /app

# Copy the configuration files and the pom.xml file
COPY backend/pom.xml .
COPY backend/src ./src

# Package the app with Maven
RUN mvn clean package -DskipTests

# Production step
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file
COPY --from=build /app/target/digitalBooking-0.0.1-SNAPSHOT.jar .

RUN groupadd -r myuser && useradd -r -g myuser myuser
USER myuser

EXPOSE 8080
CMD ["java", "-jar", "digitalBooking-0.0.1-SNAPSHOT.jar"]
