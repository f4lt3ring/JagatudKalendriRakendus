FROM openjdk:21-slim

VOLUME /tmp

ARG JAR_FILE=backend/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]