package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.domain.dto.AuthorizationDTO;
import com.gitee.taven.module.sys.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationMapper {

    User getUserByUsername(String username);

    AuthorizationDTO getUserRolePermission(String id);

}
