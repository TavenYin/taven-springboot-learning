package com.gitee.taven.module.sys.service;


import com.gitee.taven.module.sys.dto.AuthorizationDTO;
import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.entity.UserExample;


public interface UserService {

    User selectOneByExample(UserExample example);

    User getUserByUsername(String username);

    AuthorizationDTO getRolesAndPermissions(String userId);

}
