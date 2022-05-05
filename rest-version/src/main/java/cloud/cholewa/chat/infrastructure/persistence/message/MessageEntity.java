package cloud.cholewa.chat.infrastructure.persistence.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "messages")
@NamedQueries({
        @NamedQuery(name = "MessageEntity.findById", query = "select m from MessageEntity m where m.id=:id")
})
@NoArgsConstructor
@Getter
@Setter
public class MessageEntity {

    public final static String FIND_BY_ID = "findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at_date", nullable = false)
    private LocalDate createdAtDate;

    @Column(name = "created_at_time", nullable = false)
    private LocalTime createdAtTime;

    private String body;

    @Column(name = "user_id")
    private Long user_id;
}
