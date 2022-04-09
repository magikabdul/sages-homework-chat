package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.engine.channel.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_CHANNEL;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_CHAT;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_END_SESSION;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_FILE_TRANSFER;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_HISTORY;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_LOGIN;
import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_OK;
import static org.assertj.core.api.Assertions.assertThat;

class ServerMessageBuilderTest {

    private static final String messageTemplate = "channel:%s/user:%s/serverCommand:%s/messageBody:%s";

    private static final String USER_NAME = "kevin";
    private static final String CHANNEL_NAME = "sport";

    private static ServerMessageBuilder builder;


    @BeforeAll
    static void setUp() {
        User user = new User();
        user.setName(USER_NAME);
        user.setChannel(CHANNEL_NAME);

        builder = new ServerMessageBuilder(user);
    }

    @Test
    void shouldReturnWithServerCommandOkWhenServerCommandNotProvided() {

        assertThat(builder.build("", ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel")
                .containsOnlyOnce("user")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_OK)
                .containsOnlyOnce("messageBody")
                .hasSameSizeAs(messageTemplate + USER_NAME.length() + CHANNEL_NAME.length() + SERVER_COMMAND_OK);
    }

    @Test
    void shouldReturnWithServerCommandLogin() {

        assertThat(builder.build("LOGIN", ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel")
                .containsOnlyOnce("user")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_LOGIN)
                .containsOnlyOnce("messageBody");
    }

    @Test
    void shouldReturnWithServerCommandChat() {
        String message = "Simple message in chat";

        assertThat(builder.build("CHAT", message))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel")
                .containsOnlyOnce("user")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_CHAT)
                .containsOnlyOnce("messageBody")
                .containsOnlyOnce(message);
    }

    @Test
    void shouldReturnWithServerCommandEndSession() {
        String message = "Bye";

        assertThat(builder.build("END", message))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel")
                .containsOnlyOnce("user")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_END_SESSION)
                .containsOnlyOnce("messageBody")
                .containsOnlyOnce(message);
    }

    @Test
    void shouldReturnWithServerCommandChannel() {
        String message = "here and now";

        assertThat(builder.build("CHANNEL", message))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel:/")
                .containsOnlyOnce("user:/")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_CHANNEL)
                .containsOnlyOnce("messageBody")
                .containsOnlyOnce(message);
    }

    @Test
    void shouldReturnWithServerCommandHistory() {
        String message = "here and now";

        assertThat(builder.build("HISTORY", message))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel:/")
                .containsOnlyOnce("user:/")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_HISTORY)
                .containsOnlyOnce("messageBody")
                .containsOnlyOnce(message);
    }

    @Test
    void shouldReturnWithServerCommandFileTransfer() {
        String message = "Transfer started";

        assertThat(builder.build("TRANSFER", message))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("channel:/")
                .containsOnlyOnce("user:/")
                .containsOnlyOnce("serverCommand:" + SERVER_COMMAND_FILE_TRANSFER)
                .containsOnlyOnce("messageBody")
                .containsOnlyOnce(message);
    }
}
