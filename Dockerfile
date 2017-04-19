FROM rabbitmq:3.6.9-management-alpine
MAINTAINER Maksim Kostromin https://github.comm/daggerok
RUN rabbitmq-plugins enable rabbitmq_stomp --offline
EXPOSE 61613 15672
