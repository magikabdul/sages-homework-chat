package cloud.cholewa.server.engine.channel;

import java.util.List;

public interface ChatChannel {

    void addWorker(Worker Worker);

    void removeWorker(Worker Worker);

    void broadcast(String message);

    List<String> retrieveHistory();

    List<String> retrieveHistory(String date);

    void transferFile();

    void transferWorker(Worker Worker, ChatChannel chatChannel);

    String getName();

    int getNumberOfLoggedUsers();

    List<Worker> getAllWorkers();
}
