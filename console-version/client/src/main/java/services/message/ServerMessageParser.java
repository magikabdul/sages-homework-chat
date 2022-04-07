package services.message;

import lombok.RequiredArgsConstructor;
import services.User;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ServerMessageParser {

    public static final String SERVER_COMMAND_LOGIN = "LOGIN";
    public static final String SERVER_COMMAND_OK = "OK"; //sends to client to confirm that server read message
    public static final String SERVER_COMMAND_CHAT = "CHAT"; //body has message from of other client
    public static final String SERVER_COMMAND_CHANNEL = "CHANNEL";
    public static final String SERVER_COMMAND_END_SESSION = "END";

    public static final String KEY_CHANNEL_NAME = "channel";
    public static final String KEY_USER_NAME = "user";
    public static final String KEY_SERVER_COMMAND = "serverCommand";
    public static final String KEY_MESSAGE_BODY = "messageBody";

    private final Map<String, String> keysMap = new HashMap<>();

    private final User user;

    public void parseToMap(String message) {
        String[] keys = message.split("/");

        for (String key : keys) {
            int indexOfColon = key.indexOf(":");
            keysMap.put(key.substring(0, indexOfColon), key.substring(indexOfColon + 1));
        }

        user.setChannel(keysMap.get(KEY_CHANNEL_NAME));
        user.setName(keysMap.get(KEY_USER_NAME));
    }

    public String getServerCommandType() {
        return keysMap.get(KEY_SERVER_COMMAND);
    }

    public String getKeyMessageBody() {
        return keysMap.get(KEY_MESSAGE_BODY);
    }
}
