package com.github.springboot3webfluxcontext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

/**
 * 基于 webflux 的 ThreadLocal 复制方案
 */
@Slf4j
@RestController
@SpringBootApplication
public class WebfluxApplication {

	static final ThreadLocal<String> contextThreadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
//		Hooks.enableAutomaticContextPropagation();
		Schedulers.onScheduleHook("THREAD_LOCAL_PROPAGATION", scheduleHook());
		SpringApplication.run(WebfluxApplication.class, args);
	}

	public static Function<Runnable, Runnable> scheduleHook() {
		return delegate -> {
			// 执行这行的时候，还是在原来的线程
			// 在原来的线程取值
			String value = contextThreadLocal.get();
			return () -> {
				// set 到新线程的 threadLocal
				contextThreadLocal.set(value);
				try {
					delegate.run();
				} finally {
					contextThreadLocal.remove();
					log.info("remove copy value");
				}
			};
		};
	}

	@Component
	public static class ExampleWebFilter implements WebFilter {

		@Override
		public Mono<Void> filter(ServerWebExchange serverWebExchange,
								 WebFilterChain webFilterChain) {
			contextThreadLocal.set("hello world");
			log.info("set value");
			return webFilterChain.filter(serverWebExchange)
					.doFinally(signalType -> {
						contextThreadLocal.remove();
						log.info("remove value");
					});
		}
	}

	@GetMapping("test")
	public Mono<String> test() {
		return Mono.fromCallable(() -> {
			log.info("contextThreadLocal: {}", contextThreadLocal.get());
			return contextThreadLocal.get();
		});
	}

	@GetMapping("subscribeOn")
	public Mono<String> subscribeOn() {
		return Mono.fromCallable(() -> {
			log.info("contextThreadLocal: {}", contextThreadLocal.get());
			return contextThreadLocal.get();
		}).subscribeOn(Schedulers.boundedElastic())
				.doOnNext(t -> log.info("contextThreadLocal: {}", contextThreadLocal.get()));
	}

	@GetMapping("publishOn")
	public Mono<String> publishOn() {
		return Mono.fromCallable(() -> {
			log.info("contextThreadLocal: {}", contextThreadLocal.get());
			return contextThreadLocal.get();
		}).subscribeOn(Schedulers.boundedElastic())
				.publishOn(Schedulers.parallel())
				.doOnNext(t -> log.info("contextThreadLocal: {}", contextThreadLocal.get()));
	}

}
