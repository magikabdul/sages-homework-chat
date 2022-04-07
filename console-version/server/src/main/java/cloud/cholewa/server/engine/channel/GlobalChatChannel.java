package cloud.cholewa.server.engine.channel;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_CHAT;

public class GlobalChatChannel implements ChatChannel {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final String name = "";
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
    public void broadcast(Worker worker, String message) {
        workers.stream()
                .filter(w -> !w.equals(worker))
                .filter(w -> !w.getUser().getName().isBlank()) //exclude connected but not logged yet
                .forEach(w -> {
                    ChannelWriter writer = w.getWriter();
                    writer.send(SERVER_COMMAND_CHAT, message);
                    writer.send("", "");
                });
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
