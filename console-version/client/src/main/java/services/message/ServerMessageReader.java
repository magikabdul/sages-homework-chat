package services.message;

import helpers.BasicClientFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import services.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static services.message.ServerMessageParser.SERVER_COMMAND_CHAT;
import static services.message.ServerMessageParser.SERVER_COMMAND_END_SESSION;
import static services.message.ServerMessageParser.SERVER_COMMAND_LOGIN;
import static services.message.ServerMessageParser.SERVER_COMMAND_OK;

public class ServerMessageReader {

    public final static String PROMPT = "#> ";

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());
    private final ServerMessageParser parser;

    private final User user;
    private BufferedReader reader;

    public ServerMessageReader(Socket socket, User user, ServerMessageParser parser) {
        this.user = user;
        this.parser = parser;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        log.setLevel(Level.OFF);
        String message;

        try {
            while ((message = reader.readLine()) != null) {

                parser.parseToMap(message);

                switch (parser.getServerCommandType()) {
                    case SERVER_COMMAND_OK:
                        System.out.print("\n" + user.getChannel() + "/" + user.getName() + PROMPT);
                        break;
                    case SERVER_COMMAND_LOGIN:
                        System.out.print("\n" + "Please enter your name " + PROMPT);
                        break;
                    case SERVER_COMMAND_CHAT:
                        System.out.println(parser.getKeyMessageBody());
                        break;
                    case SERVER_COMMAND_END_SESSION:
                        System.out.println(parser.getKeyMessageBody());
                        reader.close();
                        break;
                    default:
                        log.error("Unrecognized server command");
                }
            }
        } catch (IOException e) {
            log.error("Reading line error form BufferedReader: " + e.getMessage());
        }
    }
}
