reactive-spring [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=master)](https://travis-ci.org/daggerok/reactive-spring)
===============

NOTE: Latest build is failing... Nedds to be fixed... Some how.

## see branches!

```bash
gradle bootRun

# or customize timeout (default is 5 seconds)
gradle clean build
bash build/libs/*.jar -Dargs=300
java -jar build/libs/*.jar -Dargs=30
porrts are allocated:
5
# cleanup
export listen="$(netstat -AaLlnW|grep -E '3000|8000|8080|8888|9999' | wc -l)"
if [ "$listen" -ne "0" ]; then echo "seems like java is allocating $listen ports"; fi;

killall -9 java
```

links:
- [project page](http://projects.spring.io/spring-framework/)
- [webflux sse](https://spring.io/blog/2017/03/08/spring-tips-server-sent-events-sse)
- [spring 5 functional web framework](https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework)
- [spring 5.0 M5](https://spring.io/blog/2017/02/23/spring-framework-5-0-m5-update)
using
- Spring 5 functional light reactive handlers
- Mono
- Flux

```bash
gradle bootRun
http :8080/1
http --stream :8080
http :8888/123
http --stream :8888
http --stream :8888/blabla
```

```bash
# using curl
curl http://localhost:8080/1 | pj
curl http://localhost:8080/
```

others:

- link:https://github.com/daggerok/sockjs-stomp-websocket-react-and-vanilla[sockjs | stomp | websocket | react]

