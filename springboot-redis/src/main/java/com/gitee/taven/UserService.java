package com.gitee.taven;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
@CacheConfig(cacheNames="user")
public class UserService {

	// 模拟mysql
	private List<User> users = new Vector<>();

	@Cacheable
	public User findUserById(Integer id) {
		System.out.println("执行了findUserById(), id = " + id);
		return users.stream().filter(e -> e.getId().equals(id)).findFirst().get();
	}

	@CachePut
	public User findUserById(User user) {
		System.out.println("执行了findUserById(), user = " + user);
		return users.stream().filter(e -> e.getId().equals(user.getId())).findFirst().get();
	}

	@CacheEvict
	public void add(User user) {
		System.out.println("执行了add(), user = " + user);
		users.add(user);
	}

}
