package services.message;

public class ClientMessageBuilder {

    private String messageTemplate = "type:%s/header:%s/body:%s";

    public static final String MESSAGE_TYPE_SYSTEM = "SYSTEM";
    public static final String MESSAGE_TYPE_CHAT = "CHAT";

    public static final String HEADER_LOGIN = "LOGIN";
    public static final String HEADER_LOGOUT = "LOGOUT";

    public String build(String messageType, String messageHeader, String messageBody) {
        if (messageType.equals(MESSAGE_TYPE_CHAT)) {
            return String.format(messageTemplate, MESSAGE_TYPE_CHAT, "", messageBody);
        }

        switch (messageHeader) {
            case HEADER_LOGIN:
                return String.format(messageTemplate, MESSAGE_TYPE_SYSTEM, HEADER_LOGIN, messageBody);
            case HEADER_LOGOUT:
                return String.format(messageTemplate, MESSAGE_TYPE_SYSTEM, HEADER_LOGOUT, "");
        }
    }
}
