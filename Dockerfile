FROM java:openjdk-8u91-jdk
VOLUME /tmp
CMD java -jar filemanager-0.0.1.jar
ADD build/libs/filemanager-0.0.1.jar .