package cloud.cholewa.chat.domain.channel.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HistoryMessage {

    private Long id;
    private String name;
    private String nick;
    private String body;
    private LocalDate createdAtDate;
    private LocalTime createdAtTime;
}
