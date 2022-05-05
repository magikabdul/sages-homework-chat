package cloud.cholewa.chat.domain.channel.model;

import cloud.cholewa.chat.domain.user.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class Message {

    private LocalDate createdAtDate;
    private LocalTime createdAtTime;
    private String body;
    private User author;

}
