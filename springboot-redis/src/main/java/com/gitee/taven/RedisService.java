package com.gitee.taven;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void test() {
//		redisTemplate.opsForHash().put(":spring:redis:ht", "name", "taven");
//		Map m = new HashMap();
//		m.put("name", "tavenyin");
//		m.put("lover", "hmj");
//		redisTemplate.opsForHash().putAll(":spring:redis:ht", m);;
//		List list = redisTemplate.opsForHash().values(":spring:redis:ht");
//		System.out.println(list);
//		redisTemplate.opsForHash().
	}

	
}
