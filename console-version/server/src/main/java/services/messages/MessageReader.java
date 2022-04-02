package services.messages;

import helpers.BasicServerFactory;
import org.apache.log4j.Logger;
import services.workers.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReader {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final Socket socket;
    private final MessageWriter messageWriter;
    private final User user;

    private BufferedReader bufferedReader;

    public MessageReader(Socket socket, MessageWriter messageWriter, User user) {
        this.socket = socket;
        this.messageWriter = messageWriter;
        this.user = user;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        messageWriter.sendText("Please enter your nickname:");

        try {

            while (true) {
                String message = bufferedReader.readLine();
                user.setLastClientMessage(message);
                user.updateUserName();

                log.info(String.format("%s - %s", user.getUserName(), message));
                if (message.endsWith("\\q")) {
                    messageWriter.sendText(String.format("Bye %s !!!", user.getUserName()));
                    socket.close();
                    break;
                }
                messageWriter.sendText("");
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
