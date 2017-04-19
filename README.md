stomp-websocket-sockjs [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=stomp-websocket-sockjs-client)](https://travis-ci.org/daggerok/reactive-spring)
======================

STOMP / SockJS chat on spring-boot backend and react frontend

links:
- [stomp doc](http://jmesnil.net/stomp-websocket/doc/)
- [stomp ws spring article (ru)](https://habrahabr.ru/post/187822/)
- [stomp](https://www.youtube.com/watch?v=mmIza3L64Ic)
- [websockets](https://www.youtube.com/watch?v=nxakp15CACY)
- [materialize.css](http://materializecss.com/)

```bash
gradle bootRun
open http://localhost:3000 $ browser 1
open http://localhost:3000 $ browser 2
```

add websocket config (see StompWebSocketMessageBrokerConfig)

```bash
2017-04-18 06:03:47.719  INFO 35638 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService  'clientInboundChannelExecutor'
2017-04-18 06:03:47.726  INFO 35638 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService  'clientOutboundChannelExecutor'
2017-04-18 06:03:47.737  INFO 35638 --- [  restartedMain] o.s.s.c.ThreadPoolTaskScheduler          : Initializing ExecutorService  'messageBrokerTaskScheduler'
2017-04-18 06:03:47.765  INFO 35638 --- [  restartedMain] o.s.w.s.s.s.WebSocketHandlerMapping      : Mapped URL path [/chat-endpoint/**] onto handler of type [class org.springframework.web.socket.sockjs.support.SockJsHttpRequestHandler]
2017-04-18 06:03:47.776  INFO 35638 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService  'brokerChannelExecutor'
...
2017-04-18 06:03:48.165  INFO 35638 --- [  restartedMain] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 2147483647
2017-04-18 06:03:48.166  INFO 35638 --- [  restartedMain] o.s.m.s.b.SimpleBrokerMessageHandler     : Starting...
2017-04-18 06:03:48.166  INFO 35638 --- [  restartedMain] o.s.m.s.b.SimpleBrokerMessageHandler     : BrokerAvailabilityEvent[available=true, SimpleBrokerMessageHandler [DefaultSubscriptionRegistry[cache[0 destination(s)], registry[0 sessions]]]]
2017-04-18 06:03:48.167  INFO 35638 --- [  restartedMain] o.s.m.s.b.SimpleBrokerMessageHandler     : Started.
...
2017-04-18 06:04:47.777  INFO 35638 --- [MessageBroker-1] o.s.w.s.c.WebSocketMessageBrokerStats    : WebSocketSession[0 current WS(0)-HttpStream(0)-HttpPoll(0), 0 total, 0 closed abnormally (0 connect failure, 0 send limit, 0 transport error)], stompSubProtocol[processed CONNECT(0)-CONNECTED(0)-DISCONNECT(0)], stompBrokerRelay[null], inboundChannel[pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0], outboundChannelpool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0], sockJsScheduler[pool size = 1, active threads = 1, queued tasks = 0, completed tasks = 0]
```

add aop monitor logger (see MessageLogAspect class);

```bash
```

add executable (see build.gradle bootJar task)

```bash
gradle clean build
bash build/libs/reactive-router-webflux-0.0.1.jar 
bash build/libs/reactive-router-webflux-0.0.1.jar --spring.profiles.active=dev 
```
