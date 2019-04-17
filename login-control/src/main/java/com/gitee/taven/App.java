package com.gitee.taven;

import com.gitee.taven.filter.CompareKickOutFilter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;


@SpringBootApplication
public class App {

	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		config.setCodec(new JsonJacksonCodec())
				.useSingleServer()
				.setAddress("redis://localhost:6379");
		return Redisson.create(config);
	}

	@Bean
	public Filter kickOutFilter() {
		return new CompareKickOutFilter();
	}

	@Bean
	public FilterRegistrationBean testFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(kickOutFilter());
		registration.addUrlPatterns("/*");
		registration.setName("kickOutFilter");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
