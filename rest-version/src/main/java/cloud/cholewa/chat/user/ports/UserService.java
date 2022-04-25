package cloud.cholewa.chat.user.ports;

import cloud.cholewa.chat.user.domain.User;
import cloud.cholewa.chat.user.domain.UserRequest;

public interface UserService {

    User register(UserRequest userRequest);

    String login(UserRequest userRequest);
}
