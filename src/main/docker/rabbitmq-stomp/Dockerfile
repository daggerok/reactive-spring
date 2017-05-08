# docker rm -f rabbitmq-stomp | true
# docker build --rm --force-rm -t rabbitmq-stomp . -f src/main/docker/rabbitmq-stomp/Dockerfile
# docker run -d -p 61613:61613 -p 15672:15672 --name rabbitmq-stomp rabbitmq-stomp
FROM healthcheck/rabbitmq:alpine
RUN rabbitmq-plugins enable --offline rabbitmq_stomp rabbitmq_management
EXPOSE 61613 15672
ENV RABBITMQ_DEFAULT_USER "guest"
ENV RABBITMQ_DEFAULT_PASS "guest"
