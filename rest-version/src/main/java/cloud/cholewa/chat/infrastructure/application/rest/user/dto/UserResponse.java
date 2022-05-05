package cloud.cholewa.chat.infrastructure.application.rest.user.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String token;
}
