package services.workers;

import helpers.BasicServerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;

import java.io.IOException;

@RequiredArgsConstructor
public class ControlCommand {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    public static final String CONTROL_COMMAND_HEADER = "\\";
    public static final String END_COMMAND = "\\q";
    public static final String SWITCH_CHAT_CHANNEL = "\\c";
    public static final String SHOW_HELP = "\\h";
    public static final String DOWNLOAD_HISTORY = "\\d";

    public static final String MESSAGE_PLEASE_ENTER_YOUR_NAME = "Please enter your nickname:";

    private final ChatWorker worker;


    public void processEndCommand() {
        String userName = worker.getUser().getName();

        worker.getMessageWriter().sendText("Bye " + userName);
        log.debug(String.format("User \"%s\" has left server", userName));
        worker.getWorkers().remove(worker);
        worker.getWorkers().broadcast(String.format("User \"%s\" has left server", userName), worker);
        try {
            worker.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processSwitchChatChannel() {
        String userName = worker.getUser().getName();

        log.debug(String.format("User \"%s\" asked for channel change", userName));
    }

    public void processShowHelp() {
        String userName = worker.getUser().getName();

        log.debug(String.format("User \"%s\" asked for help", userName));
    }

    public void processDownloadHistory() {
        String userName = worker.getUser().getName();

        log.debug(String.format("User \"%s\" is downloading history", userName));
    }
}
