# docker rm -f app | true
# docker build --rm --force-rm -t app . -f src/main/docker/app/Dockerfile
# docker run -d -p 80:8080 --name app app
FROM frolvlad/alpine-oraclejdk8:8.121.13-cleaned
MAINTAINER daggerok
COPY ./build/libs /opt
RUN apk --no-cache add bash
ENTRYPOINT /bin/bash /opt/*.jar
EXPOSE 8080
VOLUME "/opt"
ENV DB_HOST "127.0.0.1"
ENV DB_PORT "27017"
