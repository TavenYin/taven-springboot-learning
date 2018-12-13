package com.gitee.taven.module.sys.service;

import com.gitee.taven.module.sys.entity.UserDO;
import com.gitee.taven.module.sys.entity.UserDOExample;

public interface UserService {

    UserDO selectOneByExample(UserDOExample example);

}
