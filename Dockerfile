# Dockerfile
FROM gradle:jdk23
WORKDIR /app
COPY build/libs/ktor-trial-all.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
