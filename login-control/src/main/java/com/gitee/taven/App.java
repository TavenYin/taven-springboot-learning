package com.gitee.taven;

import com.gitee.taven.filter.CompareKickOutFilter;
import com.gitee.taven.filter.KickOutFilter;
import com.gitee.taven.filter.QueueKickOutFilter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


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

	@ConditionalOnProperty(value = {"queue-filter.enabled"})
	@Bean
	public KickOutFilter queueKickOutFilter() {
		return new QueueKickOutFilter();
	}

	@ConditionalOnMissingBean(KickOutFilter.class)
	@Bean
	public KickOutFilter compareKickOutFilter() {
		return new CompareKickOutFilter();
	}

	@Bean
	public FilterRegistrationBean testFilterRegistration(KickOutFilter kickOutFilter) {
		System.out.println(kickOutFilter);
		FilterRegistrationBean registration = new FilterRegistrationBean(kickOutFilter);
		registration.addUrlPatterns("/user/*");
		registration.setName("kickOutFilter");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
