package cloud.cholewa.chat.infrastructure.persistence.channel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQuery(name = ChannelEntity.FIND_BY_NAME, query = "SELECT c FROM ChannelEntity c WHERE c.name LIKE :name")
@Entity
@Table(name = "channels")
@NoArgsConstructor
@Getter
@Setter
public class ChannelEntity {

    public final static String FIND_BY_NAME = "findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
