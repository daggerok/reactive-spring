# spring-webflux  [![build](https://travis-ci.org/daggerok/reactive-spring.svg?branch=master)](https://travis-ci.org/daggerok/reactive-spring)
modern reactive and non-blocking java web apps

```java
@Component
class ApplicationHandlers {

  /** Functional rendering thymeleaf template */
  Mono<RenderingResponse> index(final ServerRequest request) {
    return create("index").modelAttribute("message", "Hello, World!")
                          .build()
        ;
  }

/*// same index rendering...
  Mono<ServerResponse> index(final ServerRequest request) {
    return ok().contentType(TEXT_HTML)
               .render("index", singletonMap("message", "Hello, World!"))
        ;
  }
*/

  /** Functional webflux REST API endpoint */
  Mono<ServerResponse> api(final ServerRequest request) {
    return ok().contentType(APPLICATION_JSON_UTF8)
               .body(Mono.just("Hello World!"), String.class)
        ;
  }
}

@Configuration
@EnableWebFlux
public class Application {

  @Bean
  RouterFunction routes(final ApplicationHandlers handlers) {
    return route(GET("/api/**").and(accept(APPLICATION_JSON_UTF8)), handlers::api)
        .andOther(route(GET("/").and(accept(TEXT_HTML)), handlers::index))
        ;
  }
}
```
