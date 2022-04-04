package cloud.cholewa.server;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.builders.ServerFactory;
import cloud.cholewa.server.engine.ServerEngine;
import cloud.cholewa.server.engine.channel.ChatChannel;
import cloud.cholewa.server.engine.channel.Worker;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static cloud.cholewa.server.builders.ExecutorServiceType.FIXED;

@RequiredArgsConstructor
public class ChatServer {

    private final ServerFactory factory = new BasicServerFactory();

    private final Logger log = factory.createLogger(this.getClass());
    private final ExecutorService executorService = factory.createExecutorService(FIXED, 1024);
    private final List<ChatChannel> serverChannels = factory.createChatChannelList();
    private final ServerEngine engine = factory.createServerEngine(serverChannels);

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

                Worker worker = new Worker(socket, serverChannels);
                executorService.execute(worker);
                engine.addWorker(worker);
            }
        } catch (IOException e) {
            log.error("Listening failed: " + e.getMessage());
        }
    }
}
