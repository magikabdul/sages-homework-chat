package cloud.cholewa.server.engine.channel;

import java.util.List;

public interface ChatChannel {

    void addWorker(Worker worker);

    void removeWorker(Worker worker);

    void broadcast(Worker worker, String message);

    List<String> retrieveHistory();

    List<String> retrieveHistory(String date);

    void transferFile();

    void transferWorker(Worker worker, ChatChannel chatChannel);

    String getName();

    int getNumberOfLoggedUsers();

    List<Worker> getAllWorkers();
}
