package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.message.Message;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.SneakyThrows;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class ChannelReader {

    private final Consumer<Message> processReadMessage;

    private ObjectInputStream objectInputStream;

    public ChannelReader(Socket messageSocket, Consumer<Message> processReadMessage) {
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
