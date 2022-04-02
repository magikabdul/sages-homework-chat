package services.messages;

import helpers.BasicServerFactory;
import org.apache.log4j.Logger;
import services.workers.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageWriter {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final User user;

    private PrintWriter printWriter;

    public MessageWriter(Socket socket, User user) {
        this.user = user;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Creating OutputStream error: " + e.getMessage());
        }
    }

    public void sendText(String text) {
        printWriter.println(text);
        user.setLastServerMessage(text);
    }
}
