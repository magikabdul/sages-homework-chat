package cloud.cholewa.server.engine.channel.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_BODY_KEY;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_HEADER_KEY;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_CHAT;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_KEY;
import static cloud.cholewa.server.engine.channel.message.ClientMessageParser.MESSAGE_TYPE_SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;

class ClientMessageParserTest {

    private final String messageTemplate = "messageType:%s/messageHeader:%s/messageBody:%s";
    private static ClientMessageParser parser;


    private final String messageBodyByClient = "Hello how are you?";
    private final String messageHeaderBySystem = "login";
    private final String messageBodyBySystem = "steven";

    @BeforeAll
    static void setUp() {
        parser = new ClientMessageParser();
    }

    @Test
    void shouldReturnMapWithBlankValues() {
        String message = String.format(messageTemplate, "", "", "");

        //when
        parser.parseMessageToMap(message);
        HashMap<String, String> keysMap = parser.getKeysMap();

        //then
        assertThat(keysMap.size()).isEqualTo(3);
        assertThat(keysMap).containsOnlyKeys(MESSAGE_TYPE_KEY, MESSAGE_HEADER_KEY, MESSAGE_BODY_KEY);
        assertThat(keysMap.get(MESSAGE_TYPE_KEY)).isBlank();
        assertThat(keysMap.get(MESSAGE_HEADER_KEY)).isBlank();
        assertThat(keysMap.get(MESSAGE_BODY_KEY)).isBlank();
    }

    @Test
    void shouldReturnTrueIfHaveSystemMessage() {
        String message = String.format(messageTemplate, MESSAGE_TYPE_SYSTEM, messageHeaderBySystem, messageBodyBySystem);

        //when
        parser.parseMessageToMap(message);

        //then
        assertThat(parser.isSystemMessage(message)).isTrue();
    }

    @Test
    void shouldReturnMapWithValuesForSystemMessageType() {
        String message = String.format(messageTemplate, MESSAGE_TYPE_SYSTEM, messageHeaderBySystem, messageBodyBySystem);

        //when
        parser.parseMessageToMap(message);

        //then
        assertThat(parser.parseSystemMessage(message))
                .isInstanceOf(HashMap.class)
                .containsOnlyKeys(MESSAGE_HEADER_KEY, MESSAGE_BODY_KEY)
                .containsEntry(MESSAGE_HEADER_KEY, messageHeaderBySystem)
                .containsEntry(MESSAGE_BODY_KEY, messageBodyBySystem);

    }

    @Test
    void shouldReturnMapWithValuesForChatMessageType() {
        String message = String.format(messageTemplate, MESSAGE_TYPE_CHAT, "", messageBodyByClient);

        //when
        parser.parseMessageToMap(message);

        //then
        assertThat(parser.parseClientMessage(message))
                .isInstanceOf(HashMap.class)
                .containsOnlyKeys(MESSAGE_BODY_KEY)
                .containsEntry(MESSAGE_BODY_KEY, messageBodyByClient);
    }
}
