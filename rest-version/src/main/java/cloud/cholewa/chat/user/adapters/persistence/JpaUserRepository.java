package cloud.cholewa.chat.user.adapters.persistence;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
public class JpaUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity save(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    public Optional<UserEntity> findByNick(String nick) {
        try {
            return Optional.of(
                    entityManager.createNamedQuery(
                                    UserEntity.FIND_BY_NICK, UserEntity.class)
                            .setParameter("nick", nick)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
