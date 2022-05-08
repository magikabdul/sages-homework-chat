package cloud.cholewa.rest_client.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MessageHistoryDto {

    @JsonIgnore
    private Long id;
    private String name;
    private String nick;
    private String body;
    private String createdAtDate;
    private String createdAtTime;
}
