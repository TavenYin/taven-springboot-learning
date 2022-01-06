package com.github.taven.rest.controller;

import com.github.taven.rest.http.RequestConfigHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author tianwen.yin
 */
@RestController
@Slf4j
public class TestController {

    @Resource(name = "customTimeoutRestTemplate")
    private RestTemplate customTimeoutRestTemplate;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @GetMapping("rest/normalRequest")
    public String restNormalRequest() {
        return restTemplate.getForObject("http://localhost:8080/slowRequest", String.class);
    }

    @GetMapping("custom/normalRequest")
    public String customNormalRequest() {
        return customTimeoutRestTemplate.getForObject("http://localhost:8080/slowRequest", String.class);
    }

    @GetMapping("custom/setTimeout")
    public String customSetTimeout() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(7000).build();
        RequestConfigHolder.bind(requestConfig);
        return customTimeoutRestTemplate.getForObject("http://localhost:8080/slowRequest", String.class);
    }

    @GetMapping("slowRequest")
    public void slowRequest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(100);
    }
}
