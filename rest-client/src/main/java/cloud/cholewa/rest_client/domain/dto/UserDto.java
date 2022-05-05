package cloud.cholewa.rest_client.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String nick;
    private String password;
}
