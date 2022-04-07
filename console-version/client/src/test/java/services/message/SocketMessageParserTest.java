package services.message;

import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static services.message.SocketMessageParser.CHANNEL_NAME_KEY;
//import static services.message.SocketMessageParser.MESSAGE_BODY_KEY;
//import static services.message.SocketMessageParser.PROMPT_KEY;
//import static services.message.SocketMessageParser.SYSTEM_COMMAND_KEY;
//import static services.message.SocketMessageParser.USER_NAME_KEY;

class SocketMessageParserTest {

//    private final String messageTemplate = "channelNameIs:%s/userNameIs:%s/systemCommandIs:%s/messageBodyIs:%s/promptIs:%s";
//    String channelName = "FOOTBALL";
//    String userName = "steven";
//    String systemCommand = "New user logged to channel";
//    String messageBody = "Hi, how are U?";
//    String prompt = "#> ";
//
//    @Test
//    void shouldReturnChannelNameWhenProvided() {
//        String message = String.format(messageTemplate, channelName, "", "", "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, CHANNEL_NAME_KEY);
//
//        assertEquals("FOOTBALL", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenChannelNameNotProvided() {
//        String emptyChannelName = "";
//        String message = String.format(messageTemplate, emptyChannelName, "", "", "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, CHANNEL_NAME_KEY);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnUserNameWhenProvided() {
//        String message = String.format(messageTemplate, "", userName, "", "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, USER_NAME_KEY);
//
//        assertEquals("steven", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenUserNameNotProvided() {
//        String emptyUserName = "";
//        String message = String.format(messageTemplate, "", emptyUserName, "", "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, USER_NAME_KEY);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnSystemCommandWhenProvided() {
//        String message = String.format(messageTemplate, "", "", systemCommand, "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, SYSTEM_COMMAND_KEY);
//
//        assertEquals("New user logged to channel", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenSystemCommandNotProvided() {
//        String emptySystemCommand = "";
//        String message = String.format(messageTemplate, "", "", emptySystemCommand, "", "");
//
//        String response = SocketMessageParser.getKeyValue(message, SYSTEM_COMMAND_KEY);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnMessageBodyWhenProvided() {
//        String message = String.format(messageTemplate, "", "", "", messageBody, "");
//
//        String response = SocketMessageParser.getKeyValue(message, MESSAGE_BODY_KEY);
//
//        assertEquals("Hi, how are U?", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenMessageBodyNotProvided() {
//        String emptyMessageBody = "";
//        String message = String.format(messageTemplate, "", "", "", emptyMessageBody, "");
//
//        String response = SocketMessageParser.getKeyValue(message, MESSAGE_BODY_KEY);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnPrompt() {
//        String message = String.format(messageTemplate, "", "", "", "", prompt);
//
//        String response = SocketMessageParser.getKeyValue(message, PROMPT_KEY);
//
//        assertEquals("#> ", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenPromptNotProvided() {
//        String emptyPrompt = "";
//        String message = String.format(messageTemplate, "", "", "", "", emptyPrompt);
//
//        String response = SocketMessageParser.getKeyValue(message, PROMPT_KEY);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnEmptyStringWhenMessageBodyIsEmptyAndNoOtherData() {
//        String message = String.format(messageTemplate, "", "", "", "", "");
//
//        String response = SocketMessageParser.buildConsoleOutput(message);
//
//        assertEquals("", response);
//    }
//
//    @Test
//    void shouldReturnOnlySystemCommandAndPromptInNextLine() {
//        String message = String.format(messageTemplate, channelName, userName, systemCommand, messageBody, prompt);
//
//        String response = SocketMessageParser.buildConsoleOutput(message);
//
//        assertEquals("New user logged to channel\n#> ", response);
//    }
//
//    @Test
//    void shouldReturnUserNameWithPromptWhenUserNameProvided() {
//        String message = String.format(messageTemplate, "", userName, "", messageBody, "");
//
//        String response = SocketMessageParser.buildConsoleOutput(message);
//
//        assertEquals("steven: Hi, how are U?\n#> ", response);
//    }
//
//    @Test
//    void shouldReturnChannelNameUserNameWithPromptWhenChannelNameAndUserNameProvided() {
//        String message = String.format(messageTemplate, channelName, userName, "", messageBody, "");
//
//        String response = SocketMessageParser.buildConsoleOutput(message);
//
//        assertEquals("FOOTBALL/steven: Hi, how are U?\n#> ", response);
//    }

}
