package cloud.cholewa.chat.infrastructure.persistence.message;

import cloud.cholewa.chat.domain.channel.model.HistoryMessage;
import cloud.cholewa.chat.domain.channel.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface MessageRepositoryMapper {

    @Mapping(target = "channelId", expression = "java(message.getChannel().getId())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java(message.getAuthor().getId())")
    MessageEntity toEntity(Message message);

    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "author", ignore = true)
    Message toDomain(MessageEntity messageEntity);

    HistoryMessage toDomain(HistoryMessageEntity historyMessageEntity);
}
