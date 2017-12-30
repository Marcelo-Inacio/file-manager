FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/filemanager-0.0.1.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]