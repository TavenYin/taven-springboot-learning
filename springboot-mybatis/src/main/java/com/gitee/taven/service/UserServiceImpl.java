package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;
import com.gitee.taven.mapper.UserMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectOneByExample(UserExample example) {
        List<User> list = userMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOneByExample(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public void select() {
        userMapper.selectByPrimaryKey("1");
        userMapper.selectByPrimaryKey("1");
    }

}
