package cloud.cholewa.chat.infrastructure.persistence.channel;

import cloud.cholewa.chat.domain.channel.model.Channel;
import cloud.cholewa.chat.domain.channel.port.out.ChannelRepositoryPort;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ChannelRepositoryAdapter implements ChannelRepositoryPort {

    private final ChannelJpaRepository channelRepository;
    private final ChannelRepositoryMapper channelMapper;

    @Override
    public Channel save(Channel channel) {
        return channelMapper.toDomainCreatedChannel(channelRepository.save(channelMapper.toEntity(channel)));
    }

    @Override
    public Optional<Channel> findByName(String name) {
        var optionalChannel = channelRepository.findByName(name);
        return optionalChannel.map(channelMapper::toDomainOnlyName);
    }
}
