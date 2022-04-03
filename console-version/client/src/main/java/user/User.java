package user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

    private String userName = "";
    private String lastServerMessage = "";
    private String lastClientMessage = "";

    public String getPrompt() {
        StringBuilder sb = new StringBuilder();

        if (lastServerMessage.contains("Please enter your nickname:")) {
            if (lastClientMessage.length() > 0) {
                setUserName(lastClientMessage);
                setLastServerMessage("");
            }
        }

        if (!userName.isBlank()) {
            sb.append(userName).append(" ");
        }
        sb.append("#> ");

        return sb.toString();
    }
}
