package com.github.taven;

import com.github.taven.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {
	@Autowired
	private DemoService demoService;

	@Test
	public void contextLoads() {
//		demoService.insertDB(false);
//		demoService.insertRedis(true);
		demoService.insertDBAndRedis(false);
	}

	@Test
	public void testCache() {
		String uuid = "123";
		demoService.cache(uuid);
//		demoService.removeCache(true, uuid);
		demoService.removeCacheAfter(true, uuid);
	}

}
