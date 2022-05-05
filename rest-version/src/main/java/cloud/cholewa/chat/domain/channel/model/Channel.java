package cloud.cholewa.chat.domain.channel.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Channel {

    private Long id;
    private String name;
    private Set<String> activeUsers = new HashSet<>();

    public void addActiveUser(String name) {
        activeUsers.add(name);
    }

    public void removeActiveUser(String name) {
        activeUsers.remove(name);
    }
}
