package com.gitee.taven.config.prop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="datasource.db0")
public class Db0Properties {

	private String driverClassName;
	
	private String url;
	
	private String username;
	
	private String password;

	public Map<String, Object> getProperties() {
		Map<String, Object> map = new HashMap<>();
    	map.put("driverClassName", this.getDriverClassName());
    	map.put("url", this.getUrl());
    	map.put("username", this.getUsername());
    	map.put("password", this.getPassword());
		return map;
	}
	
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
