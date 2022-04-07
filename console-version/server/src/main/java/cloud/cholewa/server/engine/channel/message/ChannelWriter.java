package cloud.cholewa.server.engine.channel.message;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.User;
import lombok.NonNull;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChannelWriter {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());
    private final ServerMessageBuilder builder;


    private PrintWriter printWriter;

    public ChannelWriter(Socket socket, User user) {
        builder = new ServerMessageBuilder(user);

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(@NonNull String systemCommand, @NonNull String messageBody) {
        printWriter.println(builder.build(systemCommand, messageBody));
    }

}
