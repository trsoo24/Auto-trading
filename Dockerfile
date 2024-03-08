FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} springbootapp.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/springbootapp.jar"]