package cloud.cholewa.server.builders;

import cloud.cholewa.server.engine.ServerEngine;
import cloud.cholewa.server.engine.channel.ChatChannel;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface ServerFactory {

    Logger createLogger(Class<?> clazz);

    ExecutorService createExecutorService(ExecutorServiceType type, int poolSize);

    List<ChatChannel> createChatChannelList();

    ServerEngine createServerEngine(List<ChatChannel> serverChannels);
}
