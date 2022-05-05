package cloud.cholewa.chat.infrastructure.persistence.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class HistoryMessageEntity {

    @Id
    private Long id;
    private String name;
    private String nick;
    private String body;

    @Column(name = "created_at_date")
    private LocalDate createdAtDate;
    @Column(name = "created_at_time")
    private LocalTime createdAtTime;
}
