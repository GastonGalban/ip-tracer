FROM openjdk:8-jdk-alpine
FROM maven:3-jdk-8

COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean install -DskipTests
ENTRYPOINT ["java", "-jar","./target/ip-tracer-0.0.1-SNAPSHOT.jar"]
