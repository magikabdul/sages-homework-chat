package services.message;

public class MessageFormatter {

    static final String CHANNEL_NAME_KEY = "channelNameIs";
    static final String USER_NAME_KEY = "userNameIs";
    static final String SYSTEM_COMMAND_KEY = "systemCommandIs";
    static final String MESSAGE_BODY_KEY = "messageBodyIs";
    static final String PROMPT_KEY = "promptIs";

    static final String NEW_LINE = "\n";
    static final String PROMPT = "#> ";

    public static String build(String message) {
        StringBuilder sb = new StringBuilder();

        String channelName = parseChannelName(message);
        String userName = parseUserName(message);
        String systemCommand = parseSystemCommand(message);
        String messageBody = parseMessageBody(message);

        if (!systemCommand.isBlank()) {
            return sb.append(systemCommand).append(NEW_LINE).append(PROMPT).toString();
        } else if (!messageBody.isBlank()) {
            if (!channelName.isBlank()) {
                sb.append(channelName).append("/");
            }
            return sb.append(userName).append(": ").append(messageBody).append(NEW_LINE).append(PROMPT).toString();
        } else if (systemCommand.isBlank() && messageBody.isBlank()) {
            return "";
        } else {
            return "Parse error - unrecognized message structure";
        }
    }

    private static String parseChannelName(String message) {
        return getKeyValue(message, CHANNEL_NAME_KEY);
    }

    private static String parseUserName(String message) {
        return getKeyValue(message, USER_NAME_KEY);
    }

    private static String parseSystemCommand(String message) {
        return getKeyValue(message, SYSTEM_COMMAND_KEY);
    }

    private static String parseMessageBody(String message) {
        return getKeyValue(message, MESSAGE_BODY_KEY);
    }

    private static String parsePrompt(String message) {
        return getKeyValue(message, PROMPT_KEY);
    }

    static String getKeyValue(String message, String key) {
        String[] keys = message.split("/");
        for (String s : keys) {
            if (s.contains(key)) {
                return s.substring(s.indexOf(":") + 1);
            }
        }
        return "";
    }
}
