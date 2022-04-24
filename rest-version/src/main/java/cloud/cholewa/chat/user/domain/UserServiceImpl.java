package cloud.cholewa.chat.user.domain;

import cloud.cholewa.chat.user.ports.UserRepository;
import cloud.cholewa.chat.user.ports.UserService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public void register(UserRequest userRequest) {
        if (isNickNotExists(userRequest.nick())) {

            var user = User.builder()
                    .nick(userRequest.nick())
                    .password(userRequest.password())
                    .build();

            userRepository.save(user);
        } else {
            throw new UserExistsException("User already exists");
        }
    }

    private boolean isNickNotExists(String nick) {
        return userRepository.findByNick(nick).isEmpty();
    }

    @Override
    public String login(UserRequest userRequest) {
        return null;
    }
}
