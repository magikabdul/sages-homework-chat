package cloud.cholewa.client.services.message;

import cloud.cholewa.client.helpers.BasicClientFactory;
import cloud.cholewa.client.services.ChatClient;
import cloud.cholewa.client.ui.Console;
import cloud.cholewa.message.Message;
import lombok.SneakyThrows;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.ObjectInputStream;

public class ServerMessageReader {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final ObjectInputStream objectInputStream;
    private final ChatClient chatClient;


    @SneakyThrows
    public ServerMessageReader(ChatClient chatClient) {
        this.chatClient = chatClient;
        objectInputStream = new ObjectInputStream(chatClient.getMessageSocket().getInputStream());
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    public void read() {
        log.setLevel(Level.OFF);

        try {
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                chatClient.setLastServerMessage(message);
                processInputMessage(message);
            }
        } catch (EOFException e) {
            Console.writeSuccessMessage(true, "Bye. User correctly logged out\n", false);
        }
    }

    public void processInputMessage(Message message) {
        switch (message.getType()) {
            case REQUEST_FOR_LOGIN:
                requestForLogin(message);
                break;
            case SERVER_CHAT:
                processServerBroadcast(message);
            default:
                showPrompt();
        }
    }

    private void processServerBroadcast(Message message) {
        Console.writeServerBroadcast(message.getUser(), message.getChannel(), message.getBody());
    }

    private void showPrompt() {
        Console.writePromptMessage(true, chatClient.getUser());
    }

    public void requestForLogin(Message message) {
        Console.writeInfoMessage(true, message.getBody(), true);
    }
}
