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
    private final String messageTemplate = "channelNameIs:%s/userNameIs:%s/systemCommandIs:%s/messageBodyIs:%s/promptIs:%s\n";

    private final User user;
    private PrintWriter printWriter;

    public ChannelWriter(Socket socket, User user) {
        this.user = user;

        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(@NonNull String channelName,
                     @NonNull String userName,
                     @NonNull String systemCommand,
                     @NonNull String messageBody) {
        printWriter.printf(messageTemplate, channelName, userName, systemCommand, messageBody, "");
    }

    public String sendPrompt() {
        return "#> ";
    }
}
