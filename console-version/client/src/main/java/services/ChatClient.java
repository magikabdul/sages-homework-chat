package services;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;
import services.message.ConsoleMessageHandler;
import services.message.SocketMessageHandler;
import user.User;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private Socket socket;

    public ChatClient(String host, int port) {
        log.debug(String.format("Chat client has started. Trying to connect to: %s:%d", host, port));

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            log.error("Socket connection error: " + e.getMessage());
        }
    }

    public void start() {
        User user = new User();
        clearConsole();

        Thread thread = new Thread(() -> new ConsoleMessageHandler().read(socket, user));
        thread.setDaemon(true);
        thread.start();

        new SocketMessageHandler(socket, user).read();

        log.debug("Client disconnected");
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
