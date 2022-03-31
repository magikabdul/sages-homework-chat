package services;

import helpers.BasicClientFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;

@RequiredArgsConstructor
public class ChatClient {

    private final Logger log = new BasicClientFactory().createLogger(this.getClass());

    private final String host;
    private final int port;

    public void start() {
        log.info(String.format("Chat client has started. Trying to connect to: %s:%d", host, port));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.error("Connection failed");
    }
}
