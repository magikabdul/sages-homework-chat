package cloud.cholewa.chat.infrastructure.application.config.chat;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.port.out.ChannelRepositoryPort;
import cloud.cholewa.chat.domain.chat.ChatService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class ChatConfiguration {

    @Inject
    private ChannelRepositoryPort channelRepository;

    @Inject
    private ChatService chatService;

    @PostConstruct
    public void init() {
        String MAIN_CHANNEL_NAME = "general";
        Channel channel;

        if (channelRepository.findByName(MAIN_CHANNEL_NAME).isEmpty()) {
            channel = new Channel();
            channel.setName("general");
            channelRepository.save(channel);
        } else {
            channel = channelRepository.findByName(MAIN_CHANNEL_NAME).get();
        }

        chatService.addChannel(channel);
    }
}
