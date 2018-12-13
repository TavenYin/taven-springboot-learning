package com.gitee.taven.module.auth.service;

import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.mapper.UserRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRelationMapper userMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

}
