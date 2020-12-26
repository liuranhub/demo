#! /bin/bash

docker ps -a | grep demo |awk '{print $1}'| xargs docker rm -f
docker rmi demo:latest
docker build -t demo:latest .

docker images | grep none |awk '{print $3}'| xargs docker rmi

docker run -d -p 80:80 -p 9090:9090 \
--env JAVA_OPTS="-Dcom.sun.management.jmxremote.rmi.port=9090 \
-Dcom.sun.management.jmxremote=true \
-Dcom.sun.management.jmxremote.port=9090 \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.local.only=false \
-Djava.rmi.server.hostname=192.168.111.73" \
--cpus=3 --memory=1024M --name demo demo:latest

docker ps | grep demo | awk '{print $1}' | xargs docker logs -f
