FROM openjdk:17

ARG JAR_FILE=*.jar
COPY /build/libs/$JAR_FILE camp-daddy-server.jar

LABEL authors="mussangdeul"

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "camp-daddy-server.jar"]