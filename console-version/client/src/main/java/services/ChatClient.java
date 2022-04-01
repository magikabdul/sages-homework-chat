package services;

import helpers.BasicClientFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.message.ConsoleMessageHandler;
import services.message.SocketMessageHandler;

import java.io.IOException;
import java.net.Socket;

@RequiredArgsConstructor
public class ChatClient {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final String host;
    private final int port;

    public void start() {
        log.info(String.format("Chat client has started. Trying to connect to: %s:%d", host, port));
        clearConsole();

        try {
            Socket socket = new Socket(host, port);
            new ConsoleMessageHandler().read(socket);
            new SocketMessageHandler(socket).read();
        } catch (IOException e) {
            log.error("Socket connection error: " + e.getMessage());
        }

        log.error("Connection failed");
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }
}
