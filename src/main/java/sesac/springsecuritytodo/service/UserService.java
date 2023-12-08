package sesac.springsecuritytodo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.springsecuritytodo.entity.UserEntity;
import sesac.springsecuritytodo.repository.UserRepository;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    public UserEntity create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments.");
        }

        final String email = userEntity.getEmail();
        if (repository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
        }

        return repository.save(userEntity); // userEntity 를 DB에 저장
    }
    public UserEntity getByCredentials(final String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }
}
