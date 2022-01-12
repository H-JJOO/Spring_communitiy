package dev.hjjoo.community.user;

import dev.hjjoo.community.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserEntity entity);
    UserEntity selUser(UserEntity entity);
    int updUser(UserEntity entity);
}
