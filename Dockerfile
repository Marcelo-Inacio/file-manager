FROM java:openjdk-8u91-jdk
MAINTAINER marcelomcl_10@hotmail.com
EXPOSE 8082
CMD java -jar filemanager-0.0.1-SNAPSHOT.jar && /bin/bash