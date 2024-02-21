package com.github.taven.springbootvthread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringbootVThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootVThreadApplication.class, args);
	}

	@RestController
	public static class HelloController {

		@Autowired
		private JdbcTemplate jdbcTemplate;

		// getUser
		@GetMapping("/getUser")
		public User getUser() {
			// 随机 0 到 200万 之间的一个数字，不包括 200万
			int id = (int) (Math.random() * 2000000);
			String sql = "SELECT * FROM t_user WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
		}
	}

}
