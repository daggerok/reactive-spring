package daggerok.domain.user.repository;

import daggerok.domain.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String> {

    Mono<User> findById(@Param("id") final String id);

    Flux<User> findByUsername(@Param("username") final String username);

    default Flux<User> findAllUsers() {
        return Flux.fromIterable(findAll());
    }
}
