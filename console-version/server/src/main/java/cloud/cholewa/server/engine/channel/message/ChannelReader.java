package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.message.Message;
import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class ChannelReader {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final ChannelWriter writer;
    private final Consumer<Message> processReadMessage;

    private ObjectInputStream objectInputStream;
    private DataInputStream dataInputStream;

    public ChannelReader(Socket messageSocket, Socket fileSocket, ChannelWriter writer, Consumer<Message> processReadMessage) {
        this.writer = writer;
        this.processReadMessage = processReadMessage;

        try {
            objectInputStream = new ObjectInputStream(messageSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    public void read() throws ConnectionLostException {
        try {
            while (true) {
                processReadMessage.accept((Message) objectInputStream.readObject());
            }
        } catch (Exception exception) {
            throw new ConnectionLostException("Client connection lost");
        }
    }
}
