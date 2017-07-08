package daggerok.data;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveRepo {

  Flux<BusinessObject> findAll();

  Mono<BusinessObject> findFirst(final Long id);

  Mono<Void> save(final Mono<BusinessObject> bo);

  Mono<Void> save(final Flux<BusinessObject> bo);
}
