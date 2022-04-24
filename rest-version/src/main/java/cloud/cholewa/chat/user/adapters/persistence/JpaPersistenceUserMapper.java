package cloud.cholewa.chat.user.adapters.persistence;

import cloud.cholewa.chat.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface JpaPersistenceUserMapper {

    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);
}
