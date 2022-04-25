package cloud.cholewa.chat.user.domain;

import cloud.cholewa.chat.user.ports.UserRepository;
import cloud.cholewa.chat.user.ports.UserService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    public User register(UserRequest userRequest) {
        if (isNickNotExists(userRequest.nick())) {

            var user = User.builder()
                    .nick(userRequest.nick())
                    .password(userRequest.password())
                    .build();

            return userRepository.save(user);
        } else {
            throw new UserExistsException("User already exists");
        }
    }

    @Override
    public String login(UserRequest userRequest) {
        Optional<User> user = userRepository.findByNick(userRequest.nick());
        System.out.println(user);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found, please register");
        } else if (areCredentialsNotCorrect(user.get(), userRequest)) {
            throw new UserCredentialException("Invalid user nick or password");
        }

        return userRepository.updateToken(user.get(), UUID.randomUUID().toString());
    }

    private boolean isNickNotExists(String nick) {
        return userRepository.findByNick(nick).isEmpty();
    }

    private boolean areCredentialsNotCorrect(User user, UserRequest userRequest) {
        return !user.getNick().equals(userRequest.nick()) && user.getPassword().equals(userRequest.password());
    }
}
