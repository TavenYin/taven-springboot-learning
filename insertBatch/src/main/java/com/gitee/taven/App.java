package com.gitee.taven;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Date;

import com.alibaba.druid.pool.DruidDataSource;
import com.gitee.taven.entity.User;
import com.gitee.taven.service.AppService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@MapperScan("com.gitee.taven.mapper")
@RestController
public class App {

	private static final int TEST_COUNT = 4;

	private static final int TEST_DATA_COUNT = 100000;

	@Autowired
	private AppService appService;
	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@GetMapping("/test")
	public Object test() {
		List<User> userList = getData();

		for (int i=0; i<TEST_COUNT; i++) {
			appService.clearData();
			appService.mybatisInsert(userList);

		}

		for (int i=0; i<TEST_COUNT; i++) {
			appService.clearData();
			appService.mybatisBatch(userList);

		}

		for (int i=0; i<TEST_COUNT; i++) {
			appService.clearData();
			appService.jdbcBatch(userList);

		}

		return "ok";
	}

	@GetMapping("/support")
	public Object support() throws SQLException {
		String mes;

		boolean isSupportBatch = dataSource.getConnection().getMetaData().supportsBatchUpdates();
		if (isSupportBatch) {
			System.out.println(mes = "JDBC 驱动支持批处理");
			return mes;

		} else {
			System.out.println(mes = "JDBC 驱动不支持批处理");
			return mes;
		}
	}

	@GetMapping("/start1")
	public Object start() {
		List<User> userList = getData();
		appService.mybatisInsert(userList);
		return "结束";
	}

	@GetMapping("/start2")
	public Object start2() {
		List<User> userList = getData();
		appService.mybatisBatch(userList);
		return "结束";
	}

	@GetMapping("/start3")
	public Object start3() {
		List<User> userList = getData();
		appService.jdbcBatch(userList);
		return "结束";
	}

	@GetMapping("/start4")
	public Object start4() throws SQLException {
		List<User> userList = getData();
		appService.nativeJdbcBatch(userList);
		return "结束";
	}

	private List<User> getData() {
		List<User> userList = new ArrayList<>();

		for (int i=0; i<TEST_DATA_COUNT; i++) {
			String uuid = get32UUID();
			User user = new User();
			user.setId(uuid);
			user.setUsername("USERNAME_" + uuid);
			user.setPassword("PWD_" + uuid);
			user.setUserType("");
			user.setIsLock("");
			user.setIsDelete("");
			user.setCreateTime(new Date());
			user.setCreateUser("");
			user.setModifyTime(new Date());
			user.setModifyuser("");
			userList.add(user);

		}

		return userList;
	}

	private List<User> getEasyData() {
		List<User> userList = new ArrayList<>();

		for (int i=0; i<TEST_DATA_COUNT; i++) {
			String x = ((Integer) i).toString();
			User user = new User();
			user.setId(x);
			user.setUsername("1");
			user.setPassword("1");
			user.setUserType("");
			user.setIsLock("");
			user.setIsDelete("");
			user.setCreateTime(new Date());
			user.setCreateUser("");
			user.setModifyTime(new Date());
			user.setModifyuser("");
			userList.add(user);

		}

		return userList;
	}

	public static String get32UUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String ranStr = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return ranStr;
	}

}
