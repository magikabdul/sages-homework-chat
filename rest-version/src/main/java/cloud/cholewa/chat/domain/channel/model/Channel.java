package cloud.cholewa.chat.domain.channel.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Channel {

    private Long id;
    private String name;
    private List<String> activeUsers = new ArrayList<>();
}
