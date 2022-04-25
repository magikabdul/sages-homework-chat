package cloud.cholewa.chat.user.ports;

import cloud.cholewa.chat.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByNick(String nick);

    String updateToken(User user, String token);
}
