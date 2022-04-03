package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.builders.BasicServerFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

import static cloud.cholewa.server.engine.channel.message.MessageDictionary.MESSAGE_PLEASE_ENTER_YOUR_NAME;

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

    public void read() {
        writer.send(MESSAGE_PLEASE_ENTER_YOUR_NAME);

        try {
            while (true) {
                String message = bufferedReader.readLine();
                //TODO can i place it in while statement?
                processReadMessage.accept(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO do add closing reader stream?

            //throw new ConnectionLostException("Client connection lost");
        }
    }
}
