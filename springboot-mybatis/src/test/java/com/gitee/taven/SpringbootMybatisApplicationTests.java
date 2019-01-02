package com.gitee.taven;

import com.gitee.taven.entity.UserExample;
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

	@Test
	public void contextLoads() {
		UserExample example = new UserExample();
		example.createCriteria()
				.andUsernameEqualTo("taven");
		userService.selectOneByExample(example);
	}

}

