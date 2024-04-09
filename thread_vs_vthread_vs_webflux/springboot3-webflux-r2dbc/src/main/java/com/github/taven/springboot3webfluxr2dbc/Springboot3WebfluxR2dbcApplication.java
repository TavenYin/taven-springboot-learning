package com.github.taven.springboot3webfluxr2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class Springboot3WebfluxR2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot3WebfluxR2dbcApplication.class, args);
	}

	@RestController
	public static class HelloController {

		@Autowired
		private DatabaseClient databaseClient;

		// 并发1000 循环3次
		// summary =   3000 in 00:00:02 = 1362.4/s Avg:   110 Min:     3 Max:  1603 Err:     0 (0.00%)
		@GetMapping("/select1")
		public Mono<String> select1Test() {
			String sql = "select 1";
			return databaseClient.sql(sql).map(row -> row.get(0, String.class)).one();
		}

		// 并发1000 循环3次
		// summary =   3000 in 00:00:04 =  847.0/s Avg:   545 Min:   502 Max:  1091 Err:     0 (0.00%)
		@GetMapping("/selectSleep")
		public Mono<String> selectSleep() {
			String sql = "select SLEEP(0.5)";
			return databaseClient.sql(sql).map(row -> row.get(0, String.class)).one();
		}

		// 并发1000 循环3次
		// summary =   3000 in 00:00:04 =  845.3/s Avg:   515 Min:   501 Max:   592 Err:     0 (0.00%)
		@GetMapping("/threadSleep")
		public Mono<String> sleepTest() {
			return Mono.delay(Duration.ofMillis(500))
					.thenReturn("Delayed response after 3 seconds");
		}

	}

}
