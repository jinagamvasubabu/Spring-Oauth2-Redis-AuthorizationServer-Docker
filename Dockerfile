FROM frolvlad/alpine-oraclejdk8:slim
EXPOSE 8081 8081
LABEL maintainer="vasubabu"
ADD ["target/AuthorizationServer*.jar", "AuthorizationServer.jar"]
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=n"
RUN sh -c 'touch /AuthorizationServer.jar'
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar AuthorizationServer.jar --spring.profiles.active=docker" ]