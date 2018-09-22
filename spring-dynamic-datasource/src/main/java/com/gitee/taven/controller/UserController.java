package com.gitee.taven.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.gitee.taven.config.DynamicRoutingDataSource;
import com.gitee.taven.entity.User;
import com.gitee.taven.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired private UserService userService;
	
	@Autowired private Environment env;
    
    @Autowired private DynamicRoutingDataSource dynamicDataSource;
	
	/**
	 * 添加数据源示例
	 * 
	 * @return
	 */
	@GetMapping("/add_data_source")
	public Object addDataSource() {
		// 构建 DataSource 属性,
		Map<String, String> map = new HashMap<>();
		map.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, 
				env.getRequiredProperty("datasource.dbx.driverClassName"));
		map.put(DruidDataSourceFactory.PROP_URL, 
				env.getRequiredProperty("datasource.dbx.url").replace("{0}", "dynamic_db2"));
		map.put(DruidDataSourceFactory.PROP_USERNAME, 
				env.getRequiredProperty("datasource.dbx.username"));
		map.put(DruidDataSourceFactory.PROP_PASSWORD, 
				env.getRequiredProperty("datasource.dbx.password"));
		map.put("database", "dynamic_db2");
		return dynamicDataSource.addDataSource(map);
	}
	
	/**
	 * 切换数据源示例
	 * 
	 * @return
	 */
	@GetMapping("/get")
	public Object get() {
		Map<String, Object> map = new HashMap<>();
		User u0 = userService.get("dynamic_db0");
		User u1 = userService.get("dynamic_db1");
		User u2 = userService.get("dynamic_db2");
		map.put("u0", u0);
		map.put("u1", u1);
		map.put("u2", u2);
		return map;
	}
	
}
