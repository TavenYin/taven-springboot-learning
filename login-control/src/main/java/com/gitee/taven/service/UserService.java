package com.gitee.taven.service;

import com.gitee.taven.pojo.UserDTO;

public interface UserService {

    UserDTO getUserByToken(String jwt);

    String buildUserInfo(UserDTO userDTO);

    void logout(String jwt);

}
