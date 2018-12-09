package com.gitee.taven.auth.shiro;

import com.gitee.taven.auth.controller.AuthController;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // 静态资源与登录请求不拦截
        chainDefinition.addPathDefinition("/js/**", "anon");
        chainDefinition.addPathDefinition("/css/**", "anon");
        chainDefinition.addPathDefinition("/img/**", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        // 用户为授权通过或者RememberMe && 包含'admin'角色
        chainDefinition.addPathDefinition("/admin/**", "user, roles[admin]");
        // 用户为授权通过或者RememberMe && 包含'document:read'权限
        chainDefinition.addPathDefinition("/docs/**", "user, perms[document:read]");
        // 用户访问所有请求 授权通过或者RememberMe
        chainDefinition.addPathDefinition("/**", "user");

//        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }

    @Bean
    public Realm realm() {
        AuthRealm realm = new AuthRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        realm.setCacheManager(ehCacheManager());
        return realm;
    }

    @Bean
    public CacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        AuthCredentialsMatcher credentialsMatcher = new AuthCredentialsMatcher(ehCacheManager());
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

//    public SessionsSecurityManager securityManager() {
//        log.debug("--------------shiro已经加载----------------");
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setCacheManager(ehCacheManager());
//        manager.setRealm(realm());
//        manager.setRememberMeManager(rememberMeManager());
//        return manager;
//    }
//
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe7777");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

}
