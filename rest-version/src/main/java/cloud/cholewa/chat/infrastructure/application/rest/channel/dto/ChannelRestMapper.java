package cloud.cholewa.chat.infrastructure.application.rest.channel.dto;

import cloud.cholewa.chat.domain.channel.model.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ChannelRestMapper {

    @Mapping(target = "activeUsers", ignore = true)
    @Mapping(target = "id", ignore = true)
    Channel toDomainFromCreateRequest(ChannelCreateRequest channelCreateRequest);
}
