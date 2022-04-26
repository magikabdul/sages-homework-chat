package cloud.cholewa.chat.domain.user.port.in;

import cloud.cholewa.chat.domain.user.model.User;

public interface UserServicePort {

    User register(User user);

    User login(User user);
}
