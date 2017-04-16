webflux [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=webflux)](https://travis-ci.org/daggerok/reactive-spring)
=======

using Mono / Flux 

```fish
gradle bootRun
http :8080/1
http --stream :8080
curl http://localhost:8080/1 | pj
curl http://localhost:8080/
```
