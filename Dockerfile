FROM openjdk:8-jre

COPY build/libs/*.jar /usr/app/
WORKDIR /usr/app
CMD java -jar *.jar
