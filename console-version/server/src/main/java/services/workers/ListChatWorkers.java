package services.workers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListChatWorkers implements ChatWorkers {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final List<ChatWorker> workerList = new ArrayList<>();

    @Override
    public void add(ChatWorker chatWorker) {
        lock.writeLock().lock();
        workerList.add(chatWorker);
        lock.writeLock().lock();
    }

    @Override
    public void remove(ChatWorker chatWorker) {
        lock.writeLock().lock();
        workerList.remove(chatWorker);
        lock.writeLock().unlock();
    }

    @Override
    public void broadcast(String message) {
//        lock.readLock().lock();

        workerList.forEach(chatWorker -> chatWorker.send(message));

 //       lock.readLock().unlock();
    }
}
