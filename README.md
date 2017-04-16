reactive-spring [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=master)](https://travis-ci.org/daggerok/reactive-spring)
===============

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
