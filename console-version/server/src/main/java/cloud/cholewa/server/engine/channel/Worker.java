package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.message.ChannelReader;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import org.apache.log4j.Logger;

import java.net.Socket;

public class Worker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final User user = new User();

    private final Socket socket;
    private ChannelWriter writer;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.debug("Running new chat worker thread");

        writer = new ChannelWriter(socket, user);

        new ChannelReader(socket, writer, this::processIncomingMessage).read();
    }

    private void processIncomingMessage(String message) {
        //TODO add channel support
        log.debug("New message form form channel: " + message);
    }
}
