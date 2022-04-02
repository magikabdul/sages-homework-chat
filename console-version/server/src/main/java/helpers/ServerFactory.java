package helpers;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;

public interface ServerFactory {

    Logger createLogger(Class<?> clazz);

    ExecutorService createExecutorService(ExecutorServiceType type, int poolSize);

    
}
