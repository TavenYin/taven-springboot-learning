package com.gitee.taven.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // 用户必须包含'admin'角色
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // 用户必须包含'document:read'权限
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        // 用户访问所有请求 必须授权
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

}
