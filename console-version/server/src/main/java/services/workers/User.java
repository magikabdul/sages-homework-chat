package services.workers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private String name = "";
    private String lastServerMessage = "";
    private String lastClientMessage = "";

    public void updateName() {
        if (lastServerMessage.equals("Please enter your nickname:")) {
            setName(lastClientMessage.substring(2));
            setLastServerMessage("");
        }
    }
}
