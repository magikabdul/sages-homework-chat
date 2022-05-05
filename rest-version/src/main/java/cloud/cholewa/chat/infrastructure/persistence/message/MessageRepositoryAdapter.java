package cloud.cholewa.chat.infrastructure.persistence.message;

import cloud.cholewa.chat.domain.channel.model.HistoryMessage;
import cloud.cholewa.chat.domain.channel.model.Message;
import cloud.cholewa.chat.domain.channel.port.out.MessageRepositoryPort;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MessageRepositoryAdapter implements MessageRepositoryPort {

    private final MessageJpaRepository messageRepository;
    private final MessageRepositoryMapper messageMapper;

    @Override
    public Message save(Message message) {
        return messageMapper.toDomain(messageRepository.save(messageMapper.toEntity(message)));
    }

    @Override
    public List<HistoryMessage> getChannelHistory(String channelName) {
        return messageRepository.getHistoryByChannel(channelName).stream()
                .map(messageMapper::toDomain)
                .collect(toList());
    }
}
