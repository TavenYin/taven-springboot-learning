package com.gitee.taven;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class App {

	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
//		config.useClusterServers()
//				.addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
		config.useSingleServer()
				.setAddress("redis://127.0.0.1:6379");
		return Redisson.create(config);
	}

	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();

		// create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
		config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
		return new RedissonSpringCacheManager(redissonClient, config);
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
