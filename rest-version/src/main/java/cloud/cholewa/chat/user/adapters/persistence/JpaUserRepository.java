package cloud.cholewa.chat.user.adapters.persistence;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class JpaUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity save(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }
}
