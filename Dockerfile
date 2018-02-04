FROM openjdk:8-jdk-alpine
RUN mkdir /home/filemanager/
VOLUME /home/filemanager/
ADD target/mapskills-filemanager.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]