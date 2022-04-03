package services.workers;

import helpers.BasicServerFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListChatWorkers implements ChatWorkers {

    private final Logger log = new BasicServerFactory().createLogger(this.getClass());

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final List<ChatWorker> workerList = new ArrayList<>();

    @Override
    public void add(ChatWorker chatWorker) {
//        lock.writeLock().lock();
        workerList.add(chatWorker);
//        lock.writeLock().lock();
    }

    @Override
    public void remove(ChatWorker chatWorker) {
//        lock.writeLock().lock();
        workerList.remove(chatWorker);
//        lock.writeLock().unlock();
    }

    @Override
    public void broadcast(String message, ChatWorker sourceWorker) {
        log.debug("Current number of active users: " + workerList.size());

        workerList.stream()
                .filter(chatWorker -> !chatWorker.equals(sourceWorker))
                .forEach(chatWorker -> chatWorker.send(message));
    }
}
