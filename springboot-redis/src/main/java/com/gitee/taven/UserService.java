package com.gitee.taven;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames="user")// cacheName 是一定要指定的属性，可以通过 @CacheConfig 声明该类的通用配置
public class UserService {

	/**
	 * 将结果缓存，当参数相同时，不会执行方法，从缓存中取
	 *
	 * @param id
	 * @return
	 */
	@Cacheable(key = "#id")
	public User findUserById(Integer id) {
		System.out.println("===> findUserById(id), id = " + id);
		return new User(id, "taven");
	}

	/**
	 * 将结果缓存，并且该方法不管缓存是否存在，每次都会执行
	 *
	 * @param user
	 * @return
	 */
	@CachePut(key = "#user.id")
	public User update(User user) {
		System.out.println("===> update(user), user = " + user);
		return user;
	}

	/**
	 * 移除缓存，根据指定key
	 *
	 * @param user
	 */
	@CacheEvict(key = "#user.id")
	public void deleteById(User user) {
		System.out.println("===> deleteById(), user = " + user);
	}

	/**
	 * 移除当前 cacheName下所有缓存
	 *
	 */
	@CacheEvict(allEntries = true)
	public void deleteAll() {
		System.out.println("===> deleteAll()");
	}

}
