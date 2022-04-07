package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PrivateChatChannel implements ChatChannel {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final String name;
    private final List<String> members = new ArrayList<>();
    private final List<Worker> workers = new ArrayList<>();

    public PrivateChatChannel(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    @Override
    public void removeWorker(Worker worker) {
        workers.remove(worker);
    }

    @Override
    public void broadcast(Worker worker, String message) {
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

    public void addMember(String name) {
        members.add(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfLoggedUsers() {
        return workers.size();
    }

    @Override
    public List<Worker> getAllWorkers() {
        return workers;
    }
}
