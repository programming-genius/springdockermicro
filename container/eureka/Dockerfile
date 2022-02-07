FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} eureka-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "eureka-1.0-SNAPSHOT.jar"]
