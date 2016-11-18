spring-boot-reactive-web [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=spring-boot-reactive-web)](https://travis-ci.org/daggerok/reactive-spring)
========================

using Mono / Flux in spring-data repositories / controllers...

```fish
docker-compose up -d
gradle bootRun
curl http://localhost:8080/users | pj
curl http://localhost:8080/users/legacy | pj
open http://localhost:8080/index.html
docker-compose down
```
