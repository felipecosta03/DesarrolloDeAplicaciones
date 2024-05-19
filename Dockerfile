FROM amazoncorretto:17-alpine-jdk

COPY target/desarrollo-de-aplicaciones-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=test", "-jar", "/app.jar"]
