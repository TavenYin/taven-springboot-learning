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

	@GetMapping("update")
	public Object update(Integer id, String name) {
		return userService.update(new User(id, name));
	}

	@GetMapping("delete")
	public Object delete(Integer id) {
		userService.deleteById(new User(id));
		return "移除缓存 key = " + id;
	}

	@GetMapping("deleteAll")
	public Object deleteAll() {
		userService.deleteAll();
		return "移除所有缓存";
	}
}
