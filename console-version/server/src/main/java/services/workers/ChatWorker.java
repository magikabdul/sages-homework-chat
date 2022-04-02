package services.workers;

import helpers.BasicServerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.messages.MessageReader;
import services.messages.MessageWriter;

import java.net.Socket;

@RequiredArgsConstructor
public class ChatWorker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final Socket socket;

    @Override
    public void run() {
        log.debug("Running new chat worker thread");

        MessageWriter messageWriter = new MessageWriter(socket);
        new MessageReader(socket, messageWriter).read();
    }
}
