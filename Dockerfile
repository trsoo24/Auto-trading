FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} springbootapp.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/springbootapp.jar"]