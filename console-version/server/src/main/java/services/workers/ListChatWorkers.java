package services.workers;

import helpers.BasicServerFactory;
import org.apache.log4j.Logger;
import services.recorder.ChatRecorder;

import java.util.ArrayList;
import java.util.List;

public class ListChatWorkers implements ChatWorkers {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());
    private final List<ChatWorker> workerList = new ArrayList<>();

    private final ChatRecorder recorder = new ChatRecorder("GlobalChat.txt");

    @Override
    public void add(ChatWorker chatWorker) {
        workerList.add(chatWorker);
    }

    @Override
    public void remove(ChatWorker chatWorker) {
        workerList.remove(chatWorker);
    }

    @Override
    public void broadcast(String message, ChatWorker sourceWorker) {
        log.debug("Current number of active users: " + workerList.size());
        recorder.record(message);

        workerList.stream()
                .filter(chatWorker -> !chatWorker.equals(sourceWorker))
                .forEach(chatWorker -> chatWorker.send(message));
    }
}
