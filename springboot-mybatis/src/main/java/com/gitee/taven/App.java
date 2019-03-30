package com.gitee.taven;

import com.gitee.taven.entity.UserExample;
import com.gitee.taven.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@MapperScan("com.gitee.taven.mapper")
@SpringBootApplication
@RestController
public class App {

	@Autowired
	private UserService userService;

	@Bean
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}

	@GetMapping("start")
	public Object start() {
//		UserExample example = new UserExample();
//		example.createCriteria()
//				.andUsernameEqualTo("taven");
//		userService.selectOneByExample(example);
//		userService.select();
//		userService.selectList();
		userService.batchMode();
		return null;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}

