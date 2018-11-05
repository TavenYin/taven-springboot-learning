package com.gitee.taven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitee.taven.entity.Users;
import com.gitee.taven.mapper.UsersMapper;
import com.gitee.taven.mapper.UsersRelationMapper;

@Service
public class UserService {

	@Autowired private UsersRelationMapper usersRelationMapper;

	@Autowired private UsersMapper usersMapper;
	
	public Users getUserByUsername(String username) {
		return usersRelationMapper.getUserByUsername(username);
	}
	
	public void insertSelective(Users u) {
		usersMapper.insertSelective(u);
	}
	
	public void updateByPrimaryKeySelective(Users u) {
		usersMapper.updateByPrimaryKeySelective(u);
	}
	
	public void updateByPrimaryKey(Users u) {
		usersMapper.updateByPrimaryKey(u);
	}
	
}
