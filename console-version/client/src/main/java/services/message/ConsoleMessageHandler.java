package services.message;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;
import user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConsoleMessageHandler {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private PrintWriter writer;

    public void read(Socket socket, User user) {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Opening OutputStream error: " + e.getMessage());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String command;
        while (true) {
            try {
                command = reader.readLine();
                user.setLastClientMessage(command);
                writer.println(command);
            } catch (IOException e) {
                log.error("Reading from BufferedReader error: " + e.getMessage());
            }
        }
    }
}
