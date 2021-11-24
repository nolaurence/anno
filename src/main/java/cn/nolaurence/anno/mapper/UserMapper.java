package cn.nolaurence.anno.mapper;

import cn.nolaurence.anno.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    User findByUsername(String username);
}
