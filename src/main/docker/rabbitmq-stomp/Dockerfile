# docker rm -f rabbitmq-stomp | true
# docker build --rm --force-rm -t rabbitmq-stomp . -f src/main/docker/rabbitmq-stomp/Dockerfile
# docker run -d -p 61613:61613 -p 15672:15672 --name rabbitmq-stomp rabbitmq-stomp
FROM rabbitmq:3.6.9-management-alpine
MAINTAINER Maksim Kostromin https://github.comm/daggerok
RUN rabbitmq-plugins enable --offline rabbitmq_stomp
EXPOSE 61613 15672
ENV RABBITMQ_DEFAULT_USER "guest"
ENV RABBITMQ_DEFAULT_PASS "guest"
