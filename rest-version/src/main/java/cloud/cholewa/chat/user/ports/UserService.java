package cloud.cholewa.chat.user.ports;

import cloud.cholewa.chat.user.domain.UserRequest;

public interface UserService {

    void register(UserRequest userRequest);

    String login(UserRequest userRequest);
}
