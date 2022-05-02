package cloud.cholewa.chat.domain.user.adapter;

import cloud.cholewa.chat.domain.chat.ChatService;
import cloud.cholewa.chat.domain.user.exceptions.ChannelException;
import cloud.cholewa.chat.domain.user.model.User;
import cloud.cholewa.chat.domain.user.port.in.UserServicePort;
import cloud.cholewa.chat.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_EXISTS;
import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_INVALID_CREDENTIALS;
import static cloud.cholewa.chat.domain.user.exceptions.UserExceptionDictionary.USER_NOT_FOUND;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserServiceAdapter implements UserServicePort {

    private final UserRepositoryPort userRepository;
    private final ChatService chatService;

    @Override
    public User register(User user) {

        if (nickNotExist(user.getNick())) {
            return userRepository.save(user);
        } else {
            throw new ChannelException(USER_EXISTS);
        }
    }

    @Override
    public User login(User user) {
        Optional<User> optionalUser = userRepository.findByNick(user.getNick());
        User authorizedUser;

        if (optionalUser.isPresent()) {
            authorizedUser = optionalUser.get();

            if (areCredentialsNotCorrect(user, authorizedUser)) {
                throw new ChannelException(USER_INVALID_CREDENTIALS);
            }

            authorizedUser.setToken(UUID.randomUUID().toString());

            chatService.getChannels().stream()
                    .filter(channel -> channel.getName().equals("general"))
                    .findFirst().orElseThrow()
                    .addActiveUser(authorizedUser.getNick());

            return userRepository.update(authorizedUser);

        } else {
            throw new ChannelException(USER_NOT_FOUND);
        }
    }

    private boolean nickNotExist(String nick) {
        return userRepository.findByNick(nick).isEmpty();
    }

    private boolean areCredentialsNotCorrect(User userCredential, User userPersisted) {
        return !userCredential.getPassword().equals(userPersisted.getPassword());
    }
}
