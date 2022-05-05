package cloud.cholewa.chat.domain.user.model;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String nick;
    private String password;
    private String token;
}
