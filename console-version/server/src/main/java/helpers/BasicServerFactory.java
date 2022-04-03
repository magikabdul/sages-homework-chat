package helpers;

import org.apache.log4j.Logger;
import services.workers.ChatWorkers;
import services.workers.ListChatWorkers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicServerFactory implements ServerFactory {

    @Override
    public Logger createLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }

    @Override
    public ExecutorService createExecutorService(ExecutorServiceType type, int poolSize) {
        switch (type) {
            case FIXED:
                return Executors.newFixedThreadPool(poolSize);
            case SCHEDULED:
                return Executors.newScheduledThreadPool(poolSize);
            default:
                return Executors.newSingleThreadExecutor();
        }
    }

    @Override
    public ChatWorkers createChatWorkers() {
        return new ListChatWorkers();
    }
}
