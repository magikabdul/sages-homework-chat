package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.engine.channel.message.MessageDictionary;
import lombok.Data;
import lombok.NoArgsConstructor;

import static cloud.cholewa.server.engine.channel.message.MessageDictionary.MESSAGE_PLEASE_ENTER_YOUR_NAME;

@Data
@NoArgsConstructor
public class User {

    private String name = "";
    private String lastServerMessage = "";
    private String lastClientMessage = "";

    public void updateName() {
        if (lastServerMessage.equals(MESSAGE_PLEASE_ENTER_YOUR_NAME)) {
            setName(lastClientMessage.substring(2));
            setLastServerMessage("");
        }
    }
}
