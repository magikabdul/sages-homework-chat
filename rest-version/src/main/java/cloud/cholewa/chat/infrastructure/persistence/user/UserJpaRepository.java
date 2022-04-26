package cloud.cholewa.chat.infrastructure.persistence.user;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
public class UserJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<UserEntity> findByNick(String nick) {
        try {
            return Optional.of(
                    entityManager.createNamedQuery(UserEntity.FIND_BY_NICK, UserEntity.class)
                            .setParameter("nick", nick)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public UserEntity save(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return findByNick(userEntity.getNick()).orElseThrow();
    }

    public UserEntity update(UserEntity userEntity) {
        var userToUpdate = findByNick(userEntity.getNick());
        if (userToUpdate.isPresent()) {
            var entity = userToUpdate.get();
            entity.setToken((userEntity.getToken()));
            entityManager.merge(entity);
            return entity;
        }
        return null;
    }
}
