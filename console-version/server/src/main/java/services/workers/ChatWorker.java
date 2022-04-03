package services.workers;

import exceptions.ConnectionLostException;
import helpers.BasicServerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.messages.MessageReader;
import services.messages.MessageWriter;

import java.io.IOException;
import java.net.Socket;

import static services.workers.ControlCommand.CONTROL_COMMAND_HEADER;
import static services.workers.ControlCommand.END_COMMAND;
import static services.workers.ControlCommand.SWITCH_CHAT_CHANNEL;
import static services.workers.ControlCommand.SHOW_HELP;

@RequiredArgsConstructor
public class ChatWorker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());
    private final User user = new User();

    private final Socket socket;
    private final ChatWorkers workers;

    private MessageWriter messageWriter;

    @Override
    public void run() {
        log.debug("Running new chat worker thread");
        messageWriter = new MessageWriter(socket, user);
        try {
            new MessageReader(socket, this::processReadMessage, messageWriter).read();
        } catch (ConnectionLostException e) {
            workers.remove(this);
        }
    }

    public void processReadMessage(String message) {
        user.setLastClientMessage(message);
        int indexOfColon = message.indexOf(":") + 2;
        String headlessMessage = message.substring(indexOfColon);

        if (user.getName().isBlank()) {
            user.updateName();
            log.debug(String.format("User \"%s\" has been logged in", user.getName()));
            workers.broadcast(String.format("User \"%s\" has been logged in", user.getName()), this);
            messageWriter.sendText("");
        } else if (headlessMessage.startsWith(CONTROL_COMMAND_HEADER)) {
            switch (headlessMessage) {
                case END_COMMAND: {
                    messageWriter.sendText("Bye " + user.getName());
                    log.debug(String.format("User \"%s\" has left server", user.getName()));
                    workers.remove(this);
                    workers.broadcast(String.format("User \"%s\" has left server", user.getName()), this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case SHOW_HELP: {
                    log.debug(String.format("User \"%s\" asked for help", user.getName()));
                    break;
                }
                case SWITCH_CHAT_CHANNEL: {
                    log.debug(String.format("User \"%s\" is changing channel", user.getName()));
                    break;
                }
                default:
                    break;
            }
        } else {
            log.info(user.getName() + ": " + headlessMessage);
            workers.broadcast(user.getName() + ": " + headlessMessage, this);
            messageWriter.sendText("");
        }
    }


    public void send(String message) {
        messageWriter.sendText(message);
        messageWriter.sendText("");
    }
}
