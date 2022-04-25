package cloud.cholewa.chat.user.adapters.persistence;

import cloud.cholewa.chat.user.domain.User;
import cloud.cholewa.chat.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class JpaUserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository userRepository;
    private final JpaPersistenceUserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity savedUser = userRepository.save(userMapper.toEntity(user));
        return userMapper.toDomain(savedUser);
    }

    @Override
    public Optional<User> findByNick(String nick) {
        Optional<UserEntity> byNick = userRepository.findByNick(nick);
        return byNick.map(userMapper::toDomain);
    }

    @Override
    public String updateToken(User user, String token) {
        UserEntity userEntity = userMapper.toEntity(user);
        userEntity.updateToken(token);

        return userRepository.update(userEntity).getToken();
    }
}
