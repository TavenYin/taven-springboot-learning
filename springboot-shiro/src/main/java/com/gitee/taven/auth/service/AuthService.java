package com.gitee.taven.auth.service;

import com.gitee.taven.sys.domain.entity.User;
import com.gitee.taven.sys.mapper.UserRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRelationMapper userMapper;

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

}
