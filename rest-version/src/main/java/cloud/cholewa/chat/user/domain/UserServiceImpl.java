package cloud.cholewa.chat.user.domain;

import cloud.cholewa.chat.user.ports.UserRepository;
import cloud.cholewa.chat.user.ports.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(UserRequest userRequest) {
        var user = User.builder().build();
//        var userRequest
//        userRepository.save();
    }

    @Override
    public String login(UserRequest userRequest) {
        return null;
    }
}
