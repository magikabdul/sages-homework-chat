package cloud.cholewa.chat.infrastructure.application.rest.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    private Long id;
    private UUID token;
}
