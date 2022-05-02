package cloud.cholewa.chat.domain.chat;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.port.out.ChannelRepositoryPort;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class Chat {

    @Inject
    private ChannelRepositoryPort channelRepository;

    @PostConstruct
    public void init() {
        String MAIN_CHANNEL_NAME = "general";

        if (channelRepository.findByName(MAIN_CHANNEL_NAME).isEmpty()) {
            Channel channel = new Channel();
            channel.setName("general");
            channelRepository.save(channel);
        }
    }
}
