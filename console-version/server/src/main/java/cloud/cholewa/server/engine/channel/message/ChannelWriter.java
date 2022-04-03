package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChannelWriter {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final User user;
    private PrintWriter printWriter;

    public ChannelWriter(Socket socket, User user) {
        this.user = user;

        //TODO if not working move inside try
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String text) {
        printWriter.println(text);
        user.setLastServerMessage(text);
    }
}
