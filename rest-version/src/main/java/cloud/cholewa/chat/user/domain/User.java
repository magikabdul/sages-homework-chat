package cloud.cholewa.chat.user.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

    String nick;
    String password;
}
