package daggerok.data;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BusinessObjectRepo implements ReactiveRepo {

  private static final ConcurrentHashMap<Long, BusinessObject> db = new ConcurrentHashMap<>();
  private static final AtomicLong sequence = new AtomicLong(1L);

  @Override
  public Flux<BusinessObject> findAll() {
    return Flux.fromStream(db.values().parallelStream());
  }

  @Override
  public Mono<BusinessObject> findFirst(final Long id) {
    return Mono.justOrEmpty(db.getOrDefault(id, null));
  }

  @Override
  public Mono<Void> save(final Mono<BusinessObject> bo) {

    return bo.doOnNext(businessObject -> db.put(sequence.get(),
                                                businessObject.setId(sequence.getAndIncrement())))
             .then();
  }

  @Override
  public Mono<Void> save(final Flux<BusinessObject> bo) {

    return bo.doOnNext(businessObject -> db.put(sequence.get(),
                                                businessObject.setId(sequence.getAndIncrement())))
             .then();
  }
}
