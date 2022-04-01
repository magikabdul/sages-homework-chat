package services.message;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketMessageHandler {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final Socket socket;
    private BufferedReader reader;

    public SocketMessageHandler(Socket socket) {
        this.socket = socket;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            log.error("Cant create InputStream for socket: " + e.getMessage());
        }
    }

    public void read() {
        while (true) {
            String command;
            try {
                command = reader.readLine();
                System.out.println(command);
            } catch (IOException e) {
                log.error("Reading line error form BufferedReader: " + e.getMessage());
            }
        }
    }
}
