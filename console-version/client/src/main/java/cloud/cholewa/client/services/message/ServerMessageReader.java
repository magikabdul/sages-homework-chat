package cloud.cholewa.client.services.message;

import cloud.cholewa.client.helpers.BasicClientFactory;
import cloud.cholewa.client.services.User;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static cloud.cholewa.client.services.message.ServerMessageParser.KEY_CHANNEL_NAME;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_CHANNEL;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_CHAT;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_END_SESSION;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_FILE_TRANSFER;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_HISTORY;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_LOGIN;
import static cloud.cholewa.client.services.message.ServerMessageParser.SERVER_COMMAND_OK;

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
                if (message.startsWith(KEY_CHANNEL_NAME)) {
                    parser.parseToMap(message);
                    log.debug("SERVER: " + message);

                    switch (parser.getServerCommandType()) {
                        case SERVER_COMMAND_OK:
                            System.out.print("\n" + user.getChannel() + "/" + user.getName() + PROMPT);
                            break;
                        case SERVER_COMMAND_LOGIN:
                            System.out.print("\n" + "Please enter your name " + PROMPT);
                            break;
                        case SERVER_COMMAND_CHANNEL:
                            System.out.println();
                            System.out.println(parser.getKeyMessageBody());
                            break;
                        case SERVER_COMMAND_CHAT:
                            System.out.println("");
                            System.out.println(parser.getKeyMessageBody());
                            break;
                        case SERVER_COMMAND_HISTORY:
                            String messageBody = parser.getKeyMessageBody();
                            if (messageBody.contains("- START -")) {
                                System.out.println();
                            }
                            System.out.println(messageBody);
                            break;
                        case SERVER_COMMAND_FILE_TRANSFER:
                            System.out.println();
                            System.out.println("File transfer ... " + parser.getKeyMessageBody());
                            break;
                        case SERVER_COMMAND_END_SESSION:
                            System.out.println(parser.getKeyMessageBody());
                            reader.close();
                            break;
                        default:
                            log.error("Unrecognized server command");
                    }
                }
            }
        } catch (IOException e) {
            log.error("Reading line error form BufferedReader: " + e.getMessage());
        }
    }
}
