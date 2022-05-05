package cloud.cholewa.chat.infrastructure.application.rest.channel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChannelCreateRequest {

    @NotNull
    @Size(min = 3, message = "Channel min length is 3")
    String name;
}
