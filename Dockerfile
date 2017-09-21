FROM openjdk:9-jre-slim

COPY build/libs/*.jar /usr/app/
WORKDIR /usr/app
CMD java -jar *.jar
