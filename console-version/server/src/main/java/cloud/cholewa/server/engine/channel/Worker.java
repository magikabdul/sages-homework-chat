package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.message.ChannelReader;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class Worker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final User user = new User();

    private final Socket socket;
    private final List<ChatChannel> serverChannels;
    private ChannelWriter writer;

    public Worker(Socket socket, List<ChatChannel> serverChannels) {
        this.socket = socket;
        this.serverChannels = serverChannels;
    }

    @Override
    public void run() {
        log.debug("Running new chat worker thread");

        writer = new ChannelWriter(socket, user);

        try {
            new ChannelReader(socket, writer, this::processIncomingMessage).read();
        } catch (ConnectionLostException e) {
            removeWorkerFromServerChannels();
            log.error("Client connection lost");
        }
    }

    private void processIncomingMessage(String message) {
        if (message != null) {
            //TODO add channel support
            log.debug("New message form form channel: " + message);
        }
    }

    private void removeWorkerFromServerChannels() {
        List<ChatChannel> channels = serverChannels.stream()
                .filter(chatChannel -> chatChannel.getAllWorkers().contains(this))
                .collect(Collectors.toList());

        channels.forEach(chatChannel -> chatChannel.removeWorker(this));
    }
}
