package cloud.cholewa.server.engine.channel.message;

import java.util.HashMap;

public class ClientMessageParser {

    public static final String MESSAGE_TYPE_SYSTEM = "SYSTEM";
    public static final String MESSAGE_TYPE_CHAT = "CHAT";

    public static final String HEADER_LOGIN = "LOGIN";
    public static final String HEADER_LOGOUT = "LOGOUT";

    public static final String KEY_MESSAGE_TYPE = "type";
    public static final String KEY_MESSAGE_HEADER = "header";
    public static final String KEY_MESSAGE_BODY = "body";

    public static final String CONTROL_COMMAND_EMPTY_BODY = "";
    public static final String CONTROL_COMMAND_END_SESSION = "\\q";
    public static final String CONTROL_COMMAND_CHANNEL_CHANGE = "\\c";
    public static final String CONTROL_COMMAND_DOWNLOAD_CHANNEL_HISTORY = "\\d";

    private final HashMap<String, String> keysMap = new HashMap<>();

    public void parseToMap(String message) {
        String[] keys = message.split("/");

        for (String key : keys) {
            int indexOfColon = key.indexOf(":");
            keysMap.put(key.substring(0, indexOfColon), key.substring(indexOfColon + 1));
        }
    }

    public String getMessageType() {
        return keysMap.get(KEY_MESSAGE_TYPE);
    }

    public String getHeader() {
        return keysMap.get(KEY_MESSAGE_HEADER);
    }

    public String getBody() {
        return keysMap.get(KEY_MESSAGE_BODY);
    }
}
