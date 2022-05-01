package cloud.cholewa.server;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.builders.ServerFactory;
import cloud.cholewa.server.engine.ServerEngine;
import cloud.cholewa.server.engine.channel.ChatChannel;
import cloud.cholewa.server.engine.channel.Worker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

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
        log.setLevel(Level.ALL);
        initServerChannelLogger();

        try {
            ServerSocket massageServerSocket = new ServerSocket(port);
            ServerSocket fileServerSocket = new ServerSocket(port + 1);
            log.debug(String.format("Server started listening on ports: %d and %d", port, port + 1));
            listen(massageServerSocket, fileServerSocket);
        } catch (IOException e) {
            log.error("Server running failed, error: " + e.getMessage());
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void listen(ServerSocket massageServerSocket, ServerSocket fileServerSocket) {
        try {
            while (true) {
                Socket messageSocket = massageServerSocket.accept();
                Socket fileSocket = fileServerSocket.accept();
                log.debug("New client connected");

                Worker worker = new Worker(messageSocket, fileSocket, serverChannels);
                executorService.execute(worker);
                engine.addWorker(worker);
            }
        } catch (IOException e) {
            log.error("Listening failed: " + e.getMessage());
        }
    }

    private void initServerChannelLogger() {
        Thread serverChannelLogger = new Thread(new ChatChannelsLogger());
        serverChannelLogger.setDaemon(true);
        serverChannelLogger.start();
    }

    class ChatChannelsLogger implements Runnable {

        @SuppressWarnings("InfiniteLoopStatement")
        @SneakyThrows
        public void run() {
            Map<String, Integer> channelStatus = new HashMap<>();

            ThreadPoolExecutor pool = (ThreadPoolExecutor) executorService;

            while (true) {
                serverChannels.forEach(chatChannel ->
                        channelStatus.put(chatChannel.getName(), chatChannel.getNumberOfLoggedUsers()));

                log.trace("-----------------------------------------------------------------------------");
                log.trace("Number of active threads: " + pool.getActiveCount());
                log.trace("-----------------------------------------------------------------------------");
                for (Map.Entry<String, Integer> e : channelStatus.entrySet()) {
                    log.trace(String.format("Server channel \"%s\" has number of active users: %d", e.getKey(), e.getValue()));
                }

                Thread.sleep(10_000);
            }
        }
    }
}
