package cloud.cholewa.rest_client.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessagePublishDto {
    private String body;
}
