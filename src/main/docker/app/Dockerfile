# docker rm -f app | true
# docker build --rm --force-rm -t app . -f src/main/docker/app/Dockerfile
# docker run -d -p 80:8080 --name app app
FROM frolvlad/alpine-oraclejdk8:8.121.13-cleaned
COPY ./build/libs /opt
RUN apk --no-cache add bash
ENV DB_HOST "127.0.0.1"
ENV DB_PORT "27017"
ENV STOMP_BROKER_RELAY_HOST "127.0.0.1"
ENV STOMP_BROKER_RELAY_USER "guest"
ENV STOMP_BROKER_RELAY_PASS "guest"
ENTRYPOINT /bin/bash /opt/*.jar -Dspring.data.mongodb.host=$DB_HOST -Dspring.data.mongodb.port=$DB_PORT
EXPOSE 8080
VOLUME "/opt"
