package cloud.cholewa.chat.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String nick;
    private String password;
}
