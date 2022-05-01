package cloud.cholewa.server.engine.channel;

import cloud.cholewa.message.Message;
import cloud.cholewa.message.MessageType;
import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.engine.channel.message.ChannelWriter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cloud.cholewa.server.engine.channel.message.ServerMessageBuilder.SERVER_COMMAND_CHAT;

public class GlobalChatChannel implements ChatChannel {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final String name = "";
    private final List<Worker> workers = new ArrayList<>();

    @Override
    public void addWorker(Worker worker) {
        lock.writeLock().lock();
        workers.add(worker);
        lock.writeLock().unlock();
    }

    @Override
    public void removeWorker(Worker worker) {
        lock.writeLock().lock();
        workers.remove(worker);
        lock.writeLock().unlock();
    }

    @Override
    public void broadcast(Worker worker, String message) {
        lock.readLock().lock();
        workers.stream()
                .filter(w -> !w.equals(worker))
                .filter(w -> !w.getUser().getName().isBlank()) //exclude connected but not logged yet
                .forEach(w -> {
                    w.getMessageWriter().send(Message.builder()
                            .user(worker.getUser().getName())
                            .channel(worker.getUser().getChannel())
                            .type(MessageType.SERVER_CHAT)
                            .body(message)
                            .build());
                });
        lock.readLock().unlock();
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
