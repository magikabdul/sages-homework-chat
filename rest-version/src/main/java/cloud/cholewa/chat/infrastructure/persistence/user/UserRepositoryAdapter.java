package cloud.cholewa.chat.infrastructure.persistence.user;

import cloud.cholewa.chat.domain.user.model.User;
import cloud.cholewa.chat.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userRepository;
    private final UserRepositoryMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findByNick(String nick) {
        var optionalUser = userRepository.findByNick(nick);
        return optionalUser.map(userMapper::toDomain);
    }

    @Override
    public User update(User user) {
        return userMapper.toDomain(userRepository.update(userMapper.toEntity(user)));
    }
}
