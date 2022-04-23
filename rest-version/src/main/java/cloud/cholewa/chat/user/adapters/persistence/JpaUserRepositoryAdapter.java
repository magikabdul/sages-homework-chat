package cloud.cholewa.chat.user.adapters.persistence;

import cloud.cholewa.chat.user.domain.User;
import cloud.cholewa.chat.user.ports.UserRepository;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@Transactional
public class JpaUserRepositoryAdapter implements UserRepository {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> find(User user) {
        return Optional.empty();
    }
}
