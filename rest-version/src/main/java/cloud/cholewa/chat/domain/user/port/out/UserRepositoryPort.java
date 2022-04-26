package cloud.cholewa.chat.domain.user.port.out;

import cloud.cholewa.chat.domain.user.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findByNick(String nick);

    User update(User user);
}
