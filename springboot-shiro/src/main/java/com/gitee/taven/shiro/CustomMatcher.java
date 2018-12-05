package com.gitee.taven.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomMatcher extends HashedCredentialsMatcher {

    // 记录当前用户密码输入错误的次数
    // 使用AtomicInteger 确保获得线程安全的数值.如果使用负载均衡，需缓存在redis中
    private Cache<String, AtomicInteger> passwordRetryCache;

    public CustomMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(userName);

        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(userName,retryCount);
        }

        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches){
            passwordRetryCache.remove(userName);
        }

        return matches;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        String credentials = "6666";
        int hashIterations = 1024;
        ByteSource salt = ByteSource.Util.bytes("taven");
        Object obj = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(obj);
    }
}
