FROM openjdk:8u111-jdk-alpine

ARG JAR_FILE=target/user-*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]