package com.gitee.taven.service;

import com.gitee.taven.pojo.UserBO;

public interface UserService {

    UserBO getUserByToken(String jwt);

    String buildUserInfo(UserBO userBO);

    void logout(String jwt);

}
