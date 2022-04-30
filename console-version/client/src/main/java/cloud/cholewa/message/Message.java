package cloud.cholewa.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 42L;

    private String user;
    private String channel;
    private MessageType type;
    private String body;
}
