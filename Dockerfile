FROM openjdk:11
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod","/app.jar"]
