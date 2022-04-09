package cloud.cholewa.server.engine.channel.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_CHAT;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;

class ClientMessageParserTest {

    private final String messageTemplate = "type:%s/header:%s/body:%s";
    private static ClientMessageParser parser;

    @BeforeAll
    static void setUp() {
        parser = new ClientMessageParser();
    }

    @Test
    void shouldReturnSystemMessageType() {
        String messageType = "SYSTEM";
        String message = String.format(messageTemplate, messageType, "", "");

        parser.parseToMap(message);

        assertThat(parser.getMessageType())
                .isNotNull()
                .isNotBlank()
                .containsOnlyOnce(MESSAGE_TYPE_SYSTEM)
                .hasSameSizeAs(MESSAGE_TYPE_SYSTEM);
    }

    @Test
    void shouldReturnChatMessageType() {
        String messageType = "CHAT";
        String message = String.format(messageTemplate, messageType, "", "");

        parser.parseToMap(message);

        assertThat(parser.getMessageType())
                .isNotNull()
                .isNotBlank()
                .containsOnlyOnce(MESSAGE_TYPE_CHAT)
                .hasSameSizeAs(MESSAGE_TYPE_CHAT);
    }

    @Test
    void shouldReturnMessageHeader() {
        String messageHeader = "Any header";
        String message = String.format(messageTemplate, "", messageHeader, "");

        parser.parseToMap(message);

        assertThat(parser.getHeader())
                .isNotNull()
                .isNotBlank()
                .containsOnlyOnce(messageHeader)
                .hasSize(messageHeader.length());
    }

    @Test
    void shouldReturnMessageBody() {
        String messageBody = "Hello how are you?";
        String message = String.format(messageTemplate, "", "", messageBody);

        parser.parseToMap(message);

        assertThat(parser.getBody())
                .isNotNull()
                .isNotBlank()
                .containsOnlyOnce(messageBody)
                .hasSize(messageBody.length());
    }

    @Test
    void shouldReturnBlankValues() {
        String message = String.format(messageTemplate, "", "", "");

        parser.parseToMap(message);

        assertThat(parser.getMessageType())
                .isNotNull()
                .isBlank();

        assertThat(parser.getHeader())
                .isNotNull()
                .isBlank();

        assertThat(parser.getBody())
                .isNotNull()
                .isBlank();
    }
}
