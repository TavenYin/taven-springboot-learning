package com.gitee.taven.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitee.taven.entity.User;
import com.gitee.taven.mapper.UserMapper;
import com.gitee.taven.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserMapper userMapper;
	
	@Override
	public User get(String targetSource) {
		return userMapper.selectByPrimaryKey(1);
	}

}
