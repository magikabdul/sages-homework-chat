package cloud.cholewa.chat.domain.chat;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.chat.port.in.ChatServicePort;
import lombok.Data;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
@Data
public class ChatService implements ChatServicePort {

    private final Set<Channel> channels = new HashSet<>();

    @Override
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    @Override
    public Set<Channel> getAllServerChannels() {
        return channels;
    }

    @Override
    public Set<Channel> getFullInfo() {
        return channels;
    }
}
