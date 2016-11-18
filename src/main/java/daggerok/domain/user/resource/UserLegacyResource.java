package daggerok.domain.user.resource;

import daggerok.domain.user.model.User;
import daggerok.domain.user.repository.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users/legacy")
public class UserLegacyResource {

    final UserMongoRepository userMongoRepository;

    @GetMapping({ "", "/"}  )
    public List<User> getAllUsers() {
        return userMongoRepository.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") final String id) {
        return userMongoRepository.findOne(id);
    }
}
