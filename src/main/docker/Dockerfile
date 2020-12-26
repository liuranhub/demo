FROM adoptopenjdk/openjdk8

ADD *.jar /opt/app/java/app.jar

WORKDIR /opt/app/java/

ENV DEFAULT_OPTS="-Dfile.encoding=UTF-8 -Duser.timezone=GMT+8 \
-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:MinRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 \
-XX:+HeapDumpOnOutOfMemoryError"

ENTRYPOINT [ "sh", "-c", "java $DEFAULT_OPTS $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app/java/app.jar" ]

