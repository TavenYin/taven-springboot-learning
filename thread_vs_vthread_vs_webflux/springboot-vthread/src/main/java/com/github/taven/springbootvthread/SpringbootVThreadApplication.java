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

		// （其实客户端连接数不需要1000，这里偷个懒设置成一样的）
		//
		// db 连接数=1000，连接池1000，并发1000 循环3次
		// mdb:
		// 虚拟线程：
		// summary =   3000 in 00:00:02 = 1482.2/s Avg:    19 Min:     0 Max:   142 Err:     0 (0.00%)
		// 平台线程（默认200 IO 线程）:
		// summary =   3000 in 00:00:02 = 1476.4/s Avg:    12 Min:     0 Max:   142 Err:     0 (0.00%)
		// 结论：差距不大
		//
		// db 连接数=1500，连接池1500，并发1500 循环3次
		// mdb:
		// 虚拟线程：
		// summary =   4500 in 00:00:02 = 2136.8/s Avg:   232 Min:     1 Max:   603 Err:    94 (2.09%)
		// 平台线程（默认200 IO 线程）：
		// summary =   4500 in 00:00:02 = 2805.5/s Avg:    90 Min:     3 Max:   576 Err:     0 (0.00%)
		// tomcat 线程数调整到 500，与 200 结果差距不大
		// 结论：平台线程胜利
		//
		// 这组虚拟线程完全发挥不出优势，DB响应太快了，select 1 也就 1ms，几乎没有 IO 等待
		@GetMapping("/select1")
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
		// tomcat 线程数调整到 500，与 200 结果差距不大
		//
		// mysql:
		// 虚拟线程：
		// summary =    622 in 00:00:28 =   22.6/s Avg: 11376 Min:   502 Max: 26506 Err:     0 (0.00%)
		// QPS 只有20多...我甚至没有耐心等它结束，因为 MySQL 驱动中使用 synchronized
		// 分析一下这里为什么 QPS 只有 22.6，首先 synchronized block 了平台线程，我这里 sleep 0.5s，所以平台线程每秒只能处理 2 个请求
		// 我本地 12 核心 CPU，虚拟线程能利用 12 个线程，所以虚拟线程每秒能处理 24 个请求，所以 QPS 只有 22.6
		//
		// 平台线程（默认200 IO 线程）：
		// summary =   3000 in 00:00:08 =  376.5/s Avg:  1790 Min:   502 Max:  2796 Err:     0 (0.00%)
		@GetMapping("/selectSleep")
		public String selectSleep() {
			String sql = "select SLEEP(0.1)";
			return jdbcTemplate.queryForObject(sql, String.class);
		}


		// sleep 500ms，并发1000 循环3次
		// 虚拟线程：
		// summary =   3000 in 00:00:04 =  847.0/s Avg:   514 Min:   500 Max:   618 Err:     0 (0.00%)
		// 平台线程（默认200 IO 线程）：
		// summary =   3000 in 00:00:08 =  372.7/s Avg:  1769 Min:   500 Max:  3001 Err:     0 (0.00%)
		// 和上面 selectSleep 的结果几乎一致
		//
		// 我们再来测试一下，降低 sleep 时间的结论
		// sleep 100ms，并发1000 循环3次
		// 虚拟线程和平台线程 QPS 都在 1300 左右
		@GetMapping("/threadSleep")
		public void sleepTest() throws InterruptedException {
//			Thread.sleep(500);
			Thread.sleep(100);
		}

		@GetMapping("/cpuTest")
		public double cpuTest() {
			int iterations = 10000000;
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
