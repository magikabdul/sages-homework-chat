package services.message;

import helpers.BasicClientFactory;
import org.apache.log4j.Logger;
import user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketMessageHandler {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());
    private final User user;

    private BufferedReader reader;

    public SocketMessageHandler(Socket socket, User user) {
        this.user = user;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        String message;

        try {
            while ((message = reader.readLine()) != null) {

                System.out.print(MessageFormatter.build(message.replace("\n", "")));
                //TODO do it in better right way :)
                if (message.contains("/systemCommandIs:Bye")) {
                    reader.close();
                    break;
                }

            }
        } catch (IOException e) {
            log.error("Reading line error form BufferedReader: " + e.getMessage());
        }
    }


}
