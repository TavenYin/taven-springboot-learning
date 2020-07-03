package com.github.taven;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@Controller
public class SpWebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpWebsocketApplication.class, args);
	}

	@GetMapping("ws")
	public String ws(Integer userId, HttpServletRequest request) {
		request.setAttribute("userId", userId);
		return "ws";
	}

	/*  ------------------- 配置Redis序列化 ------------------- */

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory lettuceConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);
        template.setValueSerializer(valueSerializer());
		template.afterPropertiesSet();
		return template;
	}

	private Jackson2JsonRedisSerializer valueSerializer() {
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}

}
