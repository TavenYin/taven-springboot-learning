package com.gitee.taven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gitee.taven.entity.business.UserInformations;
import com.gitee.taven.entity.system.Users;
import com.gitee.taven.mapper.business.UserInformationsMapper;
import com.gitee.taven.mapper.system.UsersMapper;

@Service
public class UserService {

	@Autowired private UsersMapper usersMapper;
	
	@Autowired private UserInformationsMapper userInformationsMapper;
	
	@Transactional
	public void testJTA() {
		Users u = new Users();
		u.setUsername("hmj");
		u.setPassword("hmjbest");
		usersMapper.insertSelective(u);
		
		UserInformations ui = new UserInformations();
		ui.setUserid(666l);
		ui.setEmail("dsb");
		userInformationsMapper.insertSelective(ui);
		
//		int i = 10/0;
	}
	
}
