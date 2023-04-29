FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/fitness-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} fitness.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fitness.jar"]