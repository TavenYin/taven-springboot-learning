package com.gitee.taven.uninumber;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan("com.gitee.taven.uninumber.mapper")
@RestController
public class App {

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@GetMapping("start")
	public Object start() {
		orderService.multiThread();
		return "ok";
	}
}

