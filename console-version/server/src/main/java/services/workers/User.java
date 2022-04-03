package services.workers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private String userName = "";
    private String lastServerMessage = "";
    private String lastClientMessage = "";

    public void updateUserName() {
        if (lastServerMessage.equals("Please enter your nickname:")) {
            setUserName(lastClientMessage.substring(2));
            setLastServerMessage("");
        }
    }
}
