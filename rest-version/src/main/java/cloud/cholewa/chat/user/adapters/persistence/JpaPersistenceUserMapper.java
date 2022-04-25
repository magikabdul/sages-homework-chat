package cloud.cholewa.chat.user.adapters.persistence;

import cloud.cholewa.chat.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface JpaPersistenceUserMapper {

    UserEntity toEntity(User user);

    @Mapping(target = "password", source = "password")
    @Mapping(target = "nick", source = "nick")
    User toDomain(UserEntity userEntity);
}
