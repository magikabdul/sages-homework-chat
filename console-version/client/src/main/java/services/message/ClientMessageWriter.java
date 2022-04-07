package services.message;

import helpers.BasicClientFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static services.message.ClientMessageBuilder.HEADER_LOGIN;
import static services.message.ClientMessageBuilder.MESSAGE_TYPE_CHAT;
import static services.message.ClientMessageBuilder.MESSAGE_TYPE_SYSTEM;
import static services.message.ServerMessageParser.SERVER_COMMAND_LOGIN;
import static services.message.ServerMessageParser.SERVER_COMMAND_OK;
import static services.message.ServerMessageReader.PROMPT;

@RequiredArgsConstructor
public class ClientMessageWriter {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private PrintWriter writer;
    private final ServerMessageParser parser;
    private final User user;

    public void read(Socket socket) {

        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Opening OutputStream error: " + e.getMessage());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ClientMessageBuilder builder = new ClientMessageBuilder();

        while (true) {
            try {
                String consoleMessage = reader.readLine();
                String serverCommandType = parser.getServerCommandType();

                if (consoleMessage.contains("/")) {
                    System.out.println("Invalid char in message. Usage of \"/\" is not allowed");
                    System.out.print("" + user.getChannel() + "/" + user.getName() + PROMPT);
                } else {

                    switch (serverCommandType) {
                        case SERVER_COMMAND_OK:
                            writer.println(builder.build(MESSAGE_TYPE_CHAT, "", consoleMessage));
                            break;
                        case SERVER_COMMAND_LOGIN:
                            writer.println(builder.build(MESSAGE_TYPE_SYSTEM, HEADER_LOGIN, consoleMessage));
                            break;
                    }
                }

            } catch (IOException e) {
                log.error("Reading from BufferedReader error: " + e.getMessage());
            }
        }
    }
}
