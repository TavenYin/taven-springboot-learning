package com.github.taven.springbootvthread;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

		@GetMapping("/select1Test")
		public String select1Test() {
			String sql = "select 1";
			return jdbcTemplate.queryForObject(sql, String.class);
		}

		// db连接数=1000，连接池1000，并发1000 循环3次
		// mdb:
		// 虚拟线程：
		// summary =   3000 in 00:00:04 =  846.5/s Avg:   509 Min:   501 Max:   616 Err:     0 (0.00%)
		// 平台线程（默认200 IO 线程）:
		// summary =   3000 in 00:00:08 =  377.5/s Avg:  1784 Min:   503 Max:  2542 Err:     0 (0.00%)
		// 调大 tomcat 线程数，结果差距不大
		//
		// mysql:
		// 虚拟线程：
		// summary =    622 in 00:00:28 =   22.6/s Avg: 11376 Min:   502 Max: 26506 Err:     0 (0.00%)
		// QPS 只有20多...我甚至没有耐心等它结束，因为 MySQL 驱动中使用 synchronized
		// 平台线程：
		// summary =   3000 in 00:00:08 =  376.5/s Avg:  1790 Min:   502 Max:  2796 Err:     0 (0.00%)
		//
		@GetMapping("/selectSleep")
		public String selectSleep() {
			String sql = "select SLEEP(0.5)";
			return jdbcTemplate.queryForObject(sql, String.class);
		}


		// 结果：虚拟线程胜利，记住一定要把并发数调大
		@GetMapping("sleepTest")
		public void sleepTest() throws InterruptedException {
			Thread.sleep(500);
		}

		@Autowired
		private HikariDataSource pool;

		@GetMapping("jdbcTest")
		public Integer jdbcTest() {
			try (var conn = pool.getConnection()) {
				try (Statement stmt = conn.createStatement()) {
					String sql = "select SLEEP(0.01) ";
//					String sql = "select 1";
					try (ResultSet rs = stmt.executeQuery(sql)) {
						rs.next();
						return rs.getInt(1);
					}
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

//		ExecutorService executor = Executors.newFixedThreadPool(100);

		ExecutorService executor = Executors.newCachedThreadPool();

		@GetMapping("threadPoolTest")
		public void threadPoolTest() {
			// 使用平台线程池执行任务
			List<Future<?>> futures = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				futures.add(executor.submit(this::jdbcTest));
			}
			for (Future<?> future : futures) {
				try {
					future.get();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		@GetMapping("vthreadPoolTest")
		public void vthreadPoolTest() {
			// 使用虚拟线程池执行任务
			try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
				List<Future<?>> futures = new ArrayList<>();
				for (int i = 0; i < 20; i++) {
					futures.add(executor.submit(this::jdbcTest));
				}
			}
		}

		@GetMapping("threadPoolTest2")
		public void threadPoolTest2() {
			// 使用平台线程池执行任务
			try (ExecutorService executor = Executors.newCachedThreadPool()) {
				List<Future<?>> futures = new ArrayList<>();
				for (int i = 0; i < 20; i++) {
					futures.add(executor.submit(this::jdbcTest));
				}
			}
		}

	}

}
