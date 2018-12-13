package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.dto.AuthorizationDTO;
import com.gitee.taven.module.sys.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationMapper {

    User getUserByUsername(String username);

    AuthorizationDTO getUserRolePermission(String id);

}
