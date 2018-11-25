package com.gitee.taven;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	@Autowired
	private RedisService redisService;
	
	@Test
	public void contextLoads() {
		redisService.test();
	}

}
