FROM openjdk:17-jdk
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} springbootapp.jar

ENTRYPOINT ["java", "-jar", "/springbootapp.jar"]