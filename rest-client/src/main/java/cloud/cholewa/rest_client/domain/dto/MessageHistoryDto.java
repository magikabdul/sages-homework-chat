package cloud.cholewa.rest_client.domain.dto;

import lombok.Data;

@Data
public class MessageHistoryDto {

    private String name;
    private String nick;
    private String body;
    private String createdAtDate;
    private String createdAtTime;
}
