package com.gitee.taven.service;

import com.gitee.taven.pojo.UserBO;

public interface UserService {

    String buildUserInfo(UserBO userBO);

    void logout(String jwt);

}
