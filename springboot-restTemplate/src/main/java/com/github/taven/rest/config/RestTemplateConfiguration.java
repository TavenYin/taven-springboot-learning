package com.github.taven.rest.config;

import com.github.taven.rest.http.CustomHttpContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author tianwen.yin
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean("customTimeoutRestTemplate")
    public RestTemplate customTimeout() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpContextFactory(new CustomHttpContextFactory());
        requestFactory.setReadTimeout(3000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean("restTemplate")
    public RestTemplate normal() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
        requestFactory.setReadTimeout(3000);
        return restTemplate;
    }

}
