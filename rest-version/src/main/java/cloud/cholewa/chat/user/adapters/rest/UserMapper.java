package cloud.cholewa.chat.user.adapters.rest;

import cloud.cholewa.chat.user.domain.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    UserRequest toDomain(UserRequestDto userRequestDto);


}
