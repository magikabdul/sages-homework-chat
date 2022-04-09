package cloud.cholewa.client.services.message;

import cloud.cholewa.client.services.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_LOGIN;
import static org.assertj.core.api.Assertions.assertThat;

class ServerMessageParserTest {

    private final static String messageTemplate = "channel:%s/user:%s/serverCommand:%s/messageBody:%s";

    private static ServerMessageParser serverMessageParser;
    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User();
        serverMessageParser = new ServerMessageParser(user);
    }

    @Test
    void shouldReturnEmptyServerCommandType() {
        String message = String.format(messageTemplate, "", "", "", "");

        serverMessageParser.parseToMap(message);

        assertThat(serverMessageParser.getServerCommandType())
                .isNotNull()
                .isBlank()
                .hasSize(0);
    }

    @Test
    void shouldReturnLoginServerCommandType() {
        String message = String.format(messageTemplate, "", "", "LOGIN", "");

        serverMessageParser.parseToMap(message);

        assertThat(serverMessageParser.getServerCommandType())
                .isNotNull()
                .isNotBlank()
                .hasSameSizeAs(SERVER_COMMAND_LOGIN);
    }

    @Test
    void shouldReturnMessageBody() {
        String body = "Example message body";
        String message = String.format(messageTemplate, "", "", "", body);

        serverMessageParser.parseToMap(message);

        assertThat(serverMessageParser.getMessageBody())
                .isNotNull()
                .isNotBlank()
                .containsOnlyOnce("Example message body")
                .hasSize(body.length());
    }
}
