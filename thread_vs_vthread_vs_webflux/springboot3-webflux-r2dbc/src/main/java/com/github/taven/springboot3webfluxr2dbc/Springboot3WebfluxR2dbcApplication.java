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

		@GetMapping("test")
		public Mono<String> test() {
			return Mono.delay(Duration.ofMillis(100))
					.thenReturn("Delayed response after 500 mills");
		}

		@GetMapping("/select1")
		public Mono<String> select1Test() {
			String sql = "select 1";
			return databaseClient.sql(sql).map(row -> row.get(0, String.class)).first();
		}


		@GetMapping("/selectSleep500")
		public Mono<String> selectSleep500() {
			String sql = "select SLEEP(0.5)";
			return databaseClient.sql(sql).map(row -> row.get(0, String.class)).first();
		}

		@GetMapping("/selectSleep100")
		public Mono<String> selectSleep100() {
			String sql = "select SLEEP(0.1)";
			return databaseClient.sql(sql).map(row -> row.get(0, String.class)).first();
		}

		@GetMapping("/cpuTest")
		public Mono<Double> cpuTest() {
			int iterations = 1000000;
			double pi = calculatePi(iterations);
			return Mono.just(pi);
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
