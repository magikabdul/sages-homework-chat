package services.messages;

import helpers.BasicServerFactory;
import org.apache.log4j.Logger;
import services.workers.ControlCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

import static services.workers.ControlCommand.MESSAGE_PLEASE_ENTER_YOUR_NAME;

public class MessageReader {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final Socket socket;
    private final MessageWriter messageWriter;
    private final Consumer<String> processReadMessage;

    private BufferedReader bufferedReader;

    public MessageReader(Socket socket, Consumer<String> processReadMessage, MessageWriter messageWriter) {
        this.socket = socket;
        this.processReadMessage = processReadMessage;
        this.messageWriter = messageWriter;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        messageWriter.sendText(MESSAGE_PLEASE_ENTER_YOUR_NAME);

        try {

            while (true) {
                String message = bufferedReader.readLine();
                processReadMessage.accept(message);
            }

        } catch (IOException e) {
            if (socket.isClosed()) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            log.error("Problem with creating InputStreamReader: " + e.getMessage());
        }
    }
}
