package com.github.taven;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderApplication.class, args);
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());


	/* 为了验证provider是否可以调用provider，结果显而易见，当然可以了 */

//	@DubboReference(version = "${demo.service.version}")
//	private ProviderService providerService;
//
//
//	@Bean
//	public ApplicationRunner runner() {
//		return args -> {
//			logger.info(providerService.sayHello("mercyblitz"));
//		};
//	}

}
