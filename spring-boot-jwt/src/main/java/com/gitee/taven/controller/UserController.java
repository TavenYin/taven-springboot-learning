package com.gitee.taven.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gitee.taven.JwtHelper;
import com.gitee.taven.entity.Users;
import com.gitee.taven.service.UserService;

import io.jsonwebtoken.Claims;

@RestController
public class UserController {

	@Autowired private UserService userService;
	
	@PostMapping("/user/login")
	public Object login(@RequestBody Users upojo) {
		
		Users u = userService.getUserByUsername(upojo.getUsername());
		
		if (u == null) {
			return "用户不存在";
		}
		
		if (!u.getPassword().equals(upojo.getPassword())) {
			return "用户名或者密码错误";
		}
		
		String token = JwtHelper.createJWT(u);
		u.setToken(token);
		userService.updateByPrimaryKeySelective(u);
		
		return token;
	}
	
	@PostMapping("/user/logout")
	public Object logout(HttpServletRequest request) {
		final String authHeader = request.getHeader("Auth-Token");
		Claims c = JwtHelper.getClaims(authHeader);
		String username = c.get(JwtHelper.USERNAME, String.class);
		Users u = userService.getUserByUsername(username);
		u.setToken(null);
		userService.updateByPrimaryKey(u);
		return "logout";
	}
	
	@GetMapping("/resource")
	public Object resource() {
		return "hello world";
	}
	
}
