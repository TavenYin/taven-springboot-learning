package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.dto.AuthorizationDTO;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRelationMapper {

    AuthorizationDTO getRolesAndPermissions(String userId);

}