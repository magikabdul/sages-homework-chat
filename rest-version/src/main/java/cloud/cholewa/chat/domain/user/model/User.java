package cloud.cholewa.chat.domain.user.model;

import lombok.Data;

import java.util.UUID;

@Data
public class User {

    private Long id;
    private String nick;
    private String password;
    private UUID token;
}
