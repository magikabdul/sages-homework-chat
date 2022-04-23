package cloud.cholewa.chat.user.adapters.rest;

import cloud.cholewa.chat.user.domain.UserRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserRequest toDomain(UserRequestDto userRequestDto);


}
