FROM openjdk:15-jdk-alpine
VOLUME /wp-app
ADD build/libs/wolfpack-assessment-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]