package com.github.taven.springbootvthread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
public class SpringbootVThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootVThreadApplication.class, args);
	}

	@RestController
	public static class HelloController {

		@Autowired
		private JdbcTemplate jdbcTemplate;

		@GetMapping("/select1")
		public String select1Test() {
			String sql = "select 1";
			return jdbcTemplate.queryForObject(sql, String.class);
		}

		@GetMapping("/selectSleep100")
		public String selectSleep100() {
			String sql = "select SLEEP(0.1)";
			return jdbcTemplate.queryForObject(sql, String.class);
		}

		@GetMapping("/selectSleep500")
		public String selectSleep500() {
			String sql = "select SLEEP(0.5)";
			return jdbcTemplate.queryForObject(sql, String.class);
		}

		@GetMapping("/cpuTest")
		public double cpuTest() {
			int iterations = 1000000;
			return calculatePi(iterations);
		}

		private double calculatePi(int iterations) {
			double pi = 0.0;
			double iterator = 1.0;

			for (int i = 0; i < iterations * 2; i += 2) {
				pi += (4.0 / iterator) - (4.0 / (iterator + 2));
				iterator += 4;
			}

			return pi;
		}

	}

}
