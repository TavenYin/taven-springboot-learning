package com.gitee.taven.module.auth.service;

import com.gitee.taven.module.sys.entity.User;

public interface AuthService {

    User getUserByUsername(String username);

}
