# Dockerfile
FROM gradle:jdk23
WORKDIR /app
COPY infrastructure/build/libs/infrastructure-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
