package cloud.cholewa.chat.infrastructure.persistence.message;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Optional;

@Singleton
public class MessageJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<MessageEntity> findById(Long id) {
        try {
            return Optional.of(
                    entityManager.createNamedQuery("MessageEntity.findById", MessageEntity.class)
                            .setParameter("id", id)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public MessageEntity save(MessageEntity messageEntity) {
        entityManager.persist(messageEntity);
        BigInteger singleResult = (BigInteger) entityManager.createNativeQuery(
                "SELECT lastval()").getSingleResult();

        return findById(singleResult.longValue()).orElseThrow();
    }
}
