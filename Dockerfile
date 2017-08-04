FROM intelligentpathways/java8

COPY build/libs/*.jar /usr/app/
WORKDIR /usr/app
CMD java -jar *.jar
