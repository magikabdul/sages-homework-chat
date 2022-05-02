package cloud.cholewa.chat.domain.channel.port.out;

import cloud.cholewa.chat.domain.channel.model.Channel;

import java.util.Optional;

public interface ChannelRepositoryPort {

    Channel save(Channel channel);

    Optional<Channel> findByName(String name);
}
