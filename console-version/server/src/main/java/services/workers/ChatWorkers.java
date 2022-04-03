package services.workers;

import java.util.List;

public interface ChatWorkers {

    void add(ChatWorker chatWorker);

    void remove(ChatWorker chatWorker);

    void broadcast(String message, ChatWorker sourceWorker);

    List<String> getChatHistory(ChatWorker worker);
}
