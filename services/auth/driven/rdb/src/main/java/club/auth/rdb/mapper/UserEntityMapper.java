package club.auth.rdb.mapper;

import club.auth.domain.User;
import club.auth.rdb.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User domain);
    User toDomain(UserEntity entity);
}
