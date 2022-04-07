package services;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;
import services.message.ClientMessageWriter;
import services.message.ServerMessageParser;
import services.message.ServerMessageReader;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final User user = new User();
    private final ServerMessageParser parser;
    private Socket socket;

    public ChatClient(String host, int port) {
        log.debug(String.format("Chat client has started. Trying to connect to: %s:%d", host, port));
        parser = new ServerMessageParser(user);

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            log.error("Socket connection error: " + e.getMessage());
        }
    }

    public void start() {
        clearConsole();

        Thread thread = new Thread(() -> new ClientMessageWriter(parser).read(socket));
        thread.setDaemon(true);
        thread.start();

        new ServerMessageReader(socket, user, parser).read();

        log.debug("Client disconnected");
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
