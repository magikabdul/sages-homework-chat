package cloud.cholewa.client.services.message;

import cloud.cholewa.client.helpers.BasicClientFactory;
import cloud.cholewa.client.services.ChatClient;
import cloud.cholewa.client.ui.Console;
import cloud.cholewa.message.Message;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static cloud.cholewa.message.MessageType.CLIENT_CHAT;
import static cloud.cholewa.message.MessageType.HISTORY_REQUEST;
import static cloud.cholewa.message.MessageType.REQUEST_CHANNEL_CHANGE;
import static cloud.cholewa.message.MessageType.REQUEST_END_SESSION;
import static cloud.cholewa.message.MessageType.RESPONSE_FOR_LOGIN;

public class ClientMessageWriter {

    private final static String END_SESSION = "\\q";
    private final static String CHANGE_CHANNEL = "\\c";
    private final static String GET_HISTORY = "\\h";

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private ObjectOutputStream objectOutputStream;
    private final ChatClient chatClient;

    public ClientMessageWriter(ChatClient chatClient, Socket messageSocket) {
        this.chatClient = chatClient;

        try {
            objectOutputStream = new ObjectOutputStream(messageSocket.getOutputStream());
        } catch (IOException e) {
            log.error("Opening OutputStream error: " + e.getMessage());
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    public void send() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String consoleMessage = reader.readLine();

            if (consoleMessage.contains("\\")) {
                processClientCommand(consoleMessage);
            } else {
                processServerCommand(consoleMessage);
            }
        }
    }

    private void processClientCommand(String consoleMessage) {
        if (consoleMessage.charAt(0) == '\\') {
            if (consoleMessage.startsWith(END_SESSION)) {
                handleEndSession();
            } else if (consoleMessage.startsWith(CHANGE_CHANNEL)) {
                handleChannelChange(consoleMessage);
            } else if (consoleMessage.startsWith(GET_HISTORY)) {
                requestForHistory();
            } else {
                log.error("Unsupported control command");
                Console.writePromptMessage(true, chatClient.getUser());
            }
        } else {
            Console.writeWarningMessage(true, "Invalid usage of control character \\", true);
        }
    }

    private void processServerCommand(String consoleMessage) {
        switch (chatClient.getLastServerMessage().getType()) {
            case REQUEST_FOR_LOGIN:
                handleLoginRequest(consoleMessage);
                break;
            default:
                sendChatMessage(consoleMessage);
        }
    }

    @SneakyThrows
    private void requestForHistory() {
        objectOutputStream.writeObject(Message.builder()
                .type(HISTORY_REQUEST)
                .build());
    }

    @SneakyThrows
    private void handleChannelChange(String consoleMessage) {
        objectOutputStream.writeObject(Message.builder()
                .user(chatClient.getUser().getName())
                .channel(consoleMessage.substring(2).toUpperCase())
                .type(REQUEST_CHANNEL_CHANGE)
                .build());
    }

    @SneakyThrows
    private void handleEndSession() {
        objectOutputStream.writeObject(Message.builder()
                .user(chatClient.getUser().getName())
                .channel(chatClient.getUser().getChannel())
                .type(REQUEST_END_SESSION)
                .build());
    }

    @SneakyThrows
    private void sendChatMessage(String message) {
        if (message.length() > 0) {
            objectOutputStream.writeObject(Message.builder()
                    .user(chatClient.getUser().getName())
                    .channel(chatClient.getUser().getChannel())
                    .body(message)
                    .type(CLIENT_CHAT)
                    .build());
        }
    }

    @SneakyThrows
    private void handleLoginRequest(String consoleMessage) {
        if (consoleMessage.length() <= 1) {
            Console.writeErrorMessage(true, "Login too short, use another one", true);
        } else {
            chatClient.getUser().setName(consoleMessage);
            objectOutputStream.writeObject(Message.builder()
                    .user(chatClient.getUser().getName())
                    .channel(chatClient.getUser().getChannel())
                    .type(RESPONSE_FOR_LOGIN)
                    .body("")
                    .build());
        }
    }
}
