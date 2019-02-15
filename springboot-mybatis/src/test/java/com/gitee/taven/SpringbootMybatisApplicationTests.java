package com.gitee.taven;
import java.util.Date;

import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;
import com.gitee.taven.mapper.UserMapper;
import com.gitee.taven.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Test
	public void contextLoads() {
//		UserExample example = new UserExample();
//		example.createCriteria()
//				.andUsernameEqualTo("taven");
//		userService.selectOneByExample(example);
		User user = new User();
		user.setId(null);
		user.setUsername("insertReturnId");
		user.setPassword("");
		user.setUserType("");
		user.setIsLock(null);
		user.setIsDelete(null);
		user.setCreateTime(new Date());
		user.setCreateUser("");
		user.setModifyTime(new Date());
		user.setModifyuser("");

//		userMapper.insertReturnId(user);
		userMapper.insertOrUpdateReturnId(user);// 备注：目前看来，使用uuid的话，insertOrUpdate 无法返回主键
		System.out.println(user.getId());
	}

}

