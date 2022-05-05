package cloud.cholewa.chat.infrastructure.persistence.channel;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
public class ChannelJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ChannelEntity> findByName(String name) {
        try {
            return Optional.of(
                    entityManager.createNamedQuery(ChannelEntity.FIND_BY_NAME, ChannelEntity.class)
                            .setParameter("name", name)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public ChannelEntity save(ChannelEntity channelEntity) {
        entityManager.persist(channelEntity);
        return findByName(channelEntity.getName()).orElseThrow();
    }
}
