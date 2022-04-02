package services;

import helpers.BasicServerFactory;
import helpers.ServerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.workers.ChatWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static helpers.ExecutorServiceType.FIXED;

@RequiredArgsConstructor
public class ChatServer {

    private final ServerFactory factory = new BasicServerFactory();

    private final Logger log = factory.createLogger(this.getClass());
    private final ExecutorService executorService = factory.createExecutorService(FIXED, 1024);

    private final int port;

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            log.debug(String.format("Server started listening on port: %d", port));
            listen(serverSocket);
        } catch (IOException e) {
            log.error("Server running failed, error: " + e.getMessage());
        }
    }

    private void listen(ServerSocket serverSocket) {

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                log.debug("New client connected");

                ChatWorker chatWorker = new ChatWorker(socket);
                executorService.execute(chatWorker);

            }
        } catch (IOException e) {
            log.error("Listening failed: " + e.getMessage());
        }
    }
}
