FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} zuul-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "zuul-1.0-SNAPSHOT.jar"]
