package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.engine.channel.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerMessageBuilder {

    private final User user;

    public static final String SERVER_COMMAND_LOGIN = "LOGIN";
    public static final String SERVER_COMMAND_OK = "OK"; //sends to client to confirm that server read message
    public static final String SERVER_COMMAND_CHAT = "CHAT"; //body has message from of other client
    public static final String SERVER_COMMAND_CHANNEL = "CHANNEL";
    public static final String SERVER_COMMAND_END_SESSION = "END";

    public String build(String serverCommand, String messageBody) {
        String messageTemplate = "channel:%s/user:%s/serverCommand:%s/messageBody:%s";

        switch (serverCommand) {
            case SERVER_COMMAND_LOGIN:
                return String.format(messageTemplate, user.getChannel(), user.getName(), SERVER_COMMAND_LOGIN, "");
            case SERVER_COMMAND_CHAT:
                return String.format(messageTemplate, user.getChannel(), user.getName(), SERVER_COMMAND_CHAT, messageBody);
            case SERVER_COMMAND_END_SESSION:
                return String.format(messageTemplate, user.getChannel(), user.getName(), SERVER_COMMAND_END_SESSION, messageBody);
            case SERVER_COMMAND_CHANNEL:
                return String.format(messageTemplate, "", "", SERVER_COMMAND_CHANNEL, messageBody);
        }
        return String.format(messageTemplate, user.getChannel(), user.getName(), SERVER_COMMAND_OK, "");
    }
}
