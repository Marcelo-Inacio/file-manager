FROM openjdk:8-jdk-alpine
RUN mkdir /home/filemanager/
RUN apk update && apk add --no-cache bash
VOLUME /home/filemanager/
ADD target/mapskills-filemanager.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]