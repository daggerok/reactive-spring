webflux [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=webflux)](https://travis-ci.org/daggerok/reactive-spring)
=======

using Mono / Flux 

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
