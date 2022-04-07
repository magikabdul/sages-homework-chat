package cloud.cholewa.server.builders;

import cloud.cholewa.server.engine.ServerEngine;
import cloud.cholewa.server.engine.channel.ChatChannel;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
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
    public ServerEngine createServerEngine(List<ChatChannel> serverChannels) {
        return new ServerEngine(serverChannels);
    }

    @Override
    public List<ChatChannel> createChatChannelList() {
        return new ArrayList<>();
    }
}
