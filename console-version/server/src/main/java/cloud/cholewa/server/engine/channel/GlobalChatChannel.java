package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GlobalChatChannel implements ChatChannel {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final String name = "GLOBAL";
    private final List<Worker> workers = new ArrayList<>();

    @Override
    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    @Override
    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }

    @Override
    public void broadcast(String message) {
        //TODO
    }

    @Override
    public List<String> retrieveHistory() {
        //TODO
        return null;
    }

    @Override
    public List<String> retrieveHistory(String date) {
        //TODO
        return null;
    }

    @Override
    public void transferFile() {
        //TODO
    }

    @Override
    public void transferWorker(Worker worker, ChatChannel chatChannel) {
        //TODO
    }
}
