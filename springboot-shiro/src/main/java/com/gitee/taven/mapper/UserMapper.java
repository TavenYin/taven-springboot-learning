package com.gitee.taven.mapper;

import com.gitee.taven.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    User getUserByUsername(String username);

}
