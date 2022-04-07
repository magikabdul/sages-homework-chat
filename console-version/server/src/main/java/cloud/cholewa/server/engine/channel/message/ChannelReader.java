package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.exceptions.ConnectionLostException;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_LOGIN;

public class ChannelReader {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final ChannelWriter writer;
    private final Consumer<String> processReadMessage;

    private BufferedReader bufferedReader;

    public ChannelReader(Socket socket, ChannelWriter writer, Consumer<String> processReadMessage) {
        this.writer = writer;
        this.processReadMessage = processReadMessage;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void read() throws ConnectionLostException {
        writer.send(SERVER_COMMAND_LOGIN, "");

        String message;

        try {
            while ((message = bufferedReader.readLine()) != null) {
                processReadMessage.accept(message);
            }
        } catch (IOException e) {
            bufferedReader.close();

            throw new ConnectionLostException("Client connection lost");
        }
    }
}
