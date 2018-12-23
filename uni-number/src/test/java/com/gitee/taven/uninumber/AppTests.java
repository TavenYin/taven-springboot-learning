package com.gitee.taven.uninumber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	@Autowired
	private OrderService orderService;

	@Test
	public void contextLoads() throws InterruptedException {
		orderService.multiThread();
		Thread.sleep(30000);// Junit 主线程先结束之后，子线程就不执行了，这里非常粗暴的解决一下
	}

}

