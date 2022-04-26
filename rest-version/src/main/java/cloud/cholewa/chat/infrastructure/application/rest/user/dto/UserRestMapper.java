package cloud.cholewa.chat.infrastructure.application.rest.user.dto;

import cloud.cholewa.chat.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserRestMapper {

    @Mapping(target = "nick", source = "nick")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    User toDomain(UserRequest userRequest);

    @Mapping(target = "token", ignore = true)
    UserResponse toUserResponseId(User user);

    @Mapping(target = "id", ignore = true)
    UserResponse toUserResponseToken(User user);
}
