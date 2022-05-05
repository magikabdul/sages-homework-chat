package cloud.cholewa.chat.infrastructure.persistence.message;

import cloud.cholewa.chat.domain.channel.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface MessageRepositoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user_id", expression = "java(message.getAuthor().getId())")
    MessageEntity toEntity(Message message);

    @Mapping(target = "author", ignore = true)
    Message toDomain(MessageEntity messageEntity);
}
