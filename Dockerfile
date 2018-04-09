FROM openjdk:8-jdk-alpine
RUN apk update && apk add --no-cache bash
RUN mkdir /home/filemanager/
RUN chmod -R 777 /home/filemanager/
VOLUME /home/filemanager/
ADD target/mapskills-filemanager.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]