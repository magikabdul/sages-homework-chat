package cloud.cholewa.chat.infrastructure.persistence.user;

import cloud.cholewa.chat.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserRepositoryMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(User user);

    User toDomain(UserEntity userEntity);
}
