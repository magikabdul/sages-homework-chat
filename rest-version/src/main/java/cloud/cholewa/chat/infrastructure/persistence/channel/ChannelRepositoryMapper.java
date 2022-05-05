package cloud.cholewa.chat.infrastructure.persistence.channel;

import cloud.cholewa.chat.domain.channel.model.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ChannelRepositoryMapper {

    @Mapping(target = "activeUsers", ignore = true)
    Channel toDomainOnlyName(ChannelEntity channelEntity);


    @Mapping(target = "activeUsers", ignore = true)
    Channel toDomainCreatedChannel(ChannelEntity channelEntity);

    @Mapping(target = "name", source = "name")
    ChannelEntity toEntity(Channel channel);
}
