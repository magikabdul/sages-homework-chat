package cloud.cholewa.client.services;

import cloud.cholewa.client.helpers.BasicClientFactory;
import cloud.cholewa.client.services.message.ClientMessageWriter;
import cloud.cholewa.client.services.message.ServerMessageReader;
import cloud.cholewa.client.ui.Console;
import cloud.cholewa.message.Message;
import lombok.Data;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

@Data
public class ChatClient {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final User user = new User();
    private Message lastServerMessage;

    private Socket messageSocket;
    private Socket fileSocket;

    public ChatClient(String host, int port) {
        log.debug(String.format("Chat client has started. Trying to connect to: %s:%d", host, port));

        try {
            messageSocket = new Socket(host, port);
            fileSocket = new Socket(host, port + 1);
            lastServerMessage = Message.builder().build();
        } catch (IOException e) {
            log.error("Socket connection error: " + e.getMessage());
        }
    }

    public void start() {
        Console.clear();

        Thread thread = new Thread(() -> new ClientMessageWriter(this, messageSocket).send());
        thread.setDaemon(true);
        thread.start();

        new ServerMessageReader(this).read();
    }
}
