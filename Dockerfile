FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/wallet-0.0.1-SNAPSHOT.jar wallet-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet-app.jar"]

y