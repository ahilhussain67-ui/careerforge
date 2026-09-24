# Build the application with the project's Maven wrapper.
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Run the packaged Spring Boot application with a smaller JRE image.
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/carrerforge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java -Dserver.port=${PORT:-8080} -jar app.jar"]
