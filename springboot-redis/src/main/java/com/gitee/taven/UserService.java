package com.gitee.taven;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames="user")
public class UserService {

	@Cacheable
	public User findUserById(Integer id) {
		System.out.println("执行了findUserById(), id = " + id);
		return new User(id, "taven");
	}

	@CachePut
	public User findUserById(User user) {
		System.out.println("执行了findUserById(), user = " + user);
		return new User(user.getId(), "yintianwen");
	}

	@CacheEvict
	public void add(User user) {
		System.out.println("执行了add(), user = " + user);
	}

}
