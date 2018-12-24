package com.gitee.taven.module.sys.service;

import com.gitee.taven.module.sys.entity.Role;
import com.gitee.taven.module.sys.entity.RoleExample;

public interface RoleService {

    Role selectOneByExample(RoleExample example);

}
