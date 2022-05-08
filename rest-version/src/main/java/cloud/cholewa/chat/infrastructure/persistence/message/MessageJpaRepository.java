package cloud.cholewa.chat.infrastructure.persistence.message;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
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

    public Optional<HistoryMessageEntity> getHistoryMessageById(Long id) {
        try {
            return Optional.of((HistoryMessageEntity) entityManager.createNativeQuery(
                            "SELECT messages.id, name, nick, body, created_at_date, created_at_time FROM messages " +
                                    "LEFT JOIN channels ON channel_id = channels.id " +
                                    "LEFT JOIN users ON user_id = users.id " +
                                    "WHERE messages.id=:id", HistoryMessageEntity.class)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<HistoryMessageEntity> getHistoryByChannel(String channelName) {
        List<HistoryMessageEntity> resultList = entityManager.createNativeQuery(
                "SELECT messages.id, name, nick, body, created_at_date, created_at_time FROM messages " +
                        "LEFT JOIN channels ON channel_id = channels.id " +
                        "LEFT JOIN users ON user_id = users.id " +
                        "WHERE name=:name ORDER BY created_at_date DESC, created_at_time DESC", HistoryMessageEntity.class)
                .setParameter("name", channelName)
                .getResultList();

        return resultList;
    }
}
