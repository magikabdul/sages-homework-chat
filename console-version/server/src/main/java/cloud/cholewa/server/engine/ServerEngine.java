package cloud.cholewa.server.engine;

import cloud.cholewa.server.builders.BasicServerFactory;
import cloud.cholewa.server.builders.ServerFactory;
import cloud.cholewa.server.engine.channel.ChatChannel;
import cloud.cholewa.server.engine.channel.GlobalChatChannel;
import cloud.cholewa.server.engine.channel.Worker;
import cloud.cholewa.server.helpers.ConfigurationFiller;
import org.apache.log4j.Logger;

import java.util.List;

public class ServerEngine {

    private final ServerFactory factory = new BasicServerFactory();
    private final Logger log = factory.createLogger(this.getClass());

    private final List<ChatChannel> serverChannels;
    private final GlobalChatChannel globalChatChannel;

    public ServerEngine(List<ChatChannel> serverChannels) {
        this.serverChannels = serverChannels;

        globalChatChannel = new GlobalChatChannel();
        serverChannels.add(globalChatChannel);
        initializeCustomServerConfiguration();
    }

    public void addWorker(Worker worker) {
        log.debug("New user added to global channel");
        globalChatChannel.addWorker(worker);
    }

    private void initializeCustomServerConfiguration() {
        ConfigurationFiller.initialize(serverChannels);
    }
}
