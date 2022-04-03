package services.workers;

import exceptions.ConnectionLostException;
import helpers.BasicServerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import services.messages.MessageReader;
import services.messages.MessageWriter;

import java.net.Socket;

import static services.workers.ControlCommand.CONTROL_COMMAND_HEADER;
import static services.workers.ControlCommand.DOWNLOAD_HISTORY;
import static services.workers.ControlCommand.END_COMMAND;
import static services.workers.ControlCommand.SHOW_HELP;
import static services.workers.ControlCommand.SWITCH_CHAT_CHANNEL;

@RequiredArgsConstructor
@Getter
public class ChatWorker implements Runnable {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());
    private final User user = new User();

    private final Socket socket;
    private final ChatWorkers workers;

    private ControlCommand controlCommand;
    private MessageWriter messageWriter;

    @Override
    public void run() {
        log.debug("Running new chat worker thread");
        messageWriter = new MessageWriter(socket, user);
        controlCommand = new ControlCommand(this);
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
                    controlCommand.processEndCommand();
                    break;
                }
                case SHOW_HELP: {
                    controlCommand.processShowHelp();
                    break;
                }
                case SWITCH_CHAT_CHANNEL: {
                    controlCommand.processSwitchChatChannel();
                    break;
                }
                case DOWNLOAD_HISTORY: {
                    controlCommand.processDownloadHistory();
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
