package com.gitee.taven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Autowired
	private UserService userService;

	@GetMapping("find")
	public Object find(Integer id) {
		return userService.findUserById(id);
	}

	@GetMapping("add")
	public Object add(Integer id, String name) {
		userService.add(new User(id, name));
		return "成功";
	}
}
