package cloud.cholewa.server.engine.channel.message;

import java.util.HashMap;

public class ClientMessageParser {

    static final String MESSAGE_TYPE_SYSTEM = "system";
    static final String MESSAGE_TYPE_CHAT = "chat";

    static final String MESSAGE_TYPE_KEY = "messageType";
    static final String MESSAGE_HEADER_KEY = "messageHeader";
    static final String MESSAGE_BODY_KEY = "messageBody";

    private final HashMap<String, String> keysMap = new HashMap<>();

    public boolean isSystemMessage(String message) {
        parseMessageToMap(message);
        return !keysMap.get(MESSAGE_TYPE_KEY).isBlank();
    }

    public HashMap<String, String> parseSystemMessage(String message) {
        parseMessageToMap(message);

        keysMap.entrySet()
                .removeIf(e -> e.getKey().equals(MESSAGE_TYPE_KEY)
                );

        return keysMap;
    }

    public HashMap<String, String> parseClientMessage(String message) {
        parseMessageToMap(message);

        keysMap.entrySet()
                .removeIf(e -> e.getKey().equals(MESSAGE_TYPE_KEY) || e.getKey().equals(MESSAGE_HEADER_KEY)
                );

        return keysMap;
    }


    void parseMessageToMap(String message) {
        String[] keys = message.split("/");
        int indexOfColon;

        for (String s : keys) {
            indexOfColon = s.indexOf(":");
            keysMap.put(s.substring(0, indexOfColon), s.substring(indexOfColon + 1));
        }
    }

    HashMap<String, String> getKeysMap() {
        return keysMap;
    }
}
