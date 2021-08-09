FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=build/libs/ticketingsystem-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} ticketingsystem.jar
ENTRYPOINT ["java","-jar","/ticketingsystem.jar"]
