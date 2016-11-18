package daggerok.domain.user.resource;

import daggerok.domain.user.model.User;
import daggerok.domain.user.repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserResource {

    final UserMongoRepository userMongoRepository;

    @GetMapping({ "", "/"}  )
    public Flux<User> getAllUsers() {
        return userMongoRepository.findAllUsers();
    }

    @GetMapping("{id}")
    public Mono<User> getById(@PathVariable("id") final String id) {
        return userMongoRepository.findById(id);
    }

    @GetMapping("{username}")
    public Flux<User> getAllByUsername(@PathVariable("username") final String username) {
        return userMongoRepository.findByUsername(username);
    }
}
