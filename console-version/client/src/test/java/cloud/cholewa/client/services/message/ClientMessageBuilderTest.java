package cloud.cholewa.client.services.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static cloud.cholewa.client.services.message.ClientMessageBuilder.HEADER_LOGIN;
import static cloud.cholewa.client.services.message.ClientMessageBuilder.HEADER_LOGOUT;
import static cloud.cholewa.client.services.message.ClientMessageBuilder.MESSAGE_TYPE_CHAT;
import static cloud.cholewa.client.services.message.ClientMessageBuilder.MESSAGE_TYPE_SYSTEM;
import static org.assertj.core.api.Assertions.assertThat;

class ClientMessageBuilderTest {

    private static final String messageTemplate = "type:%s/header:%s/body:%s";

    private static ClientMessageBuilder clientMessageBuilder;
    private static String messageType;
    private static String messageHeader;
    private static String messageBody;

    @BeforeAll
    static void setUp() {
        clientMessageBuilder = new ClientMessageBuilder();
        messageType = "";
        messageHeader = "";
        messageBody = "";
    }

    @Test
    void shouldReturnMessageBody() {

        assertThat(clientMessageBuilder.build("", "", ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("type:")
                .containsOnlyOnce("header:")
                .containsOnlyOnce("body")
                .hasSameSizeAs(messageTemplate);
    }

    @Test
    void shouldReturnMessageWithTypeChat() {
        messageType = "CHAT";

        assertThat(clientMessageBuilder.build(messageType, "", ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("type:")
                .containsOnlyOnce("header:")
                .containsOnlyOnce("body")
                .contains(MESSAGE_TYPE_CHAT);
    }

    @Test
    void shouldReturnMessageWithTypeSystemWhenHeaderLogin() {
        messageType = "SYSTEM";
        messageHeader = "LOGIN";

        assertThat(clientMessageBuilder.build(messageType, messageHeader, ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("type:")
                .containsOnlyOnce("header:")
                .containsOnlyOnce("body")
                .containsOnlyOnce(MESSAGE_TYPE_SYSTEM)
                .containsOnlyOnce(HEADER_LOGIN);
    }

    @Test
    void shouldReturnMessageWithTypeSystemWhenHeaderLogout() {
        messageType = "SYSTEM";
        messageHeader = "LOGOUT";

        assertThat(clientMessageBuilder.build(messageType, messageHeader, ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce("type:")
                .containsOnlyOnce("header:")
                .containsOnlyOnce("body")
                .containsOnlyOnce(MESSAGE_TYPE_SYSTEM)
                .containsOnlyOnce(HEADER_LOGOUT);
    }

    @Test
    void shouldReturnOnlyMessageTemplate() {
        messageType = "SYSTEM";

        assertThat(clientMessageBuilder.build(messageType, "", ""))
                .isNotNull()
                .isNotBlank()
                .isInstanceOf(String.class)
                .containsOnlyOnce(messageTemplate);
    }
}
