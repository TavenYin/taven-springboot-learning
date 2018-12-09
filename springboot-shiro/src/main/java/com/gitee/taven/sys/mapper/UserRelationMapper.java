package com.gitee.taven.sys.mapper;

import com.gitee.taven.sys.domain.dto.AuthorizationDTO;
import com.gitee.taven.sys.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRelationMapper {

    User getUserByUsername(String username);

    AuthorizationDTO getUserRolePermission(String id);

}
