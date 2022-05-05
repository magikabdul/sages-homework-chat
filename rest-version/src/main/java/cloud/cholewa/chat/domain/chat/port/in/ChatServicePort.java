package cloud.cholewa.chat.domain.chat.port.in;

import cloud.cholewa.chat.domain.channel.model.Channel;

import java.util.Set;

public interface ChatServicePort {

    Set<Channel> getFullInfo();

    void addChannel(Channel channel);

    Set<Channel> getAllServerChannels();
}
