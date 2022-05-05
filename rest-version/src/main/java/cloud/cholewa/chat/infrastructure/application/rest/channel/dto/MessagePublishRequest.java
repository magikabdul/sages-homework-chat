package cloud.cholewa.chat.infrastructure.application.rest.channel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MessagePublishRequest {

    @NotNull
    @Size(max = 500)
    private String body;
}
