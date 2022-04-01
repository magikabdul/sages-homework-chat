package services.message;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConsoleMessageHandler {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private PrintWriter writer;

    public void read(Socket socket) {

        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            log.error("Opening OutputStream error: " + e.getMessage());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        while (true) {
            String command;
            try {
                command = reader.readLine();
                writer.println(command);
                writer.flush();
            } catch (IOException e) {
                log.error("Reading from BufferedReader error: " + e.getMessage());
            }
        }
    }
}
