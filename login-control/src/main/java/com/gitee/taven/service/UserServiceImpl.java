package com.gitee.taven.service;

import com.gitee.taven.controller.UserController;
import com.gitee.taven.pojo.UserDTO;
import com.gitee.taven.utils.JWTUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public UserDTO getUserByToken(String jwt) {
        RBucket<UserDTO> rBucket = redissonClient.getBucket(jwt);
        return rBucket.get();
    }

    @Override
    public String buildUserInfo(UserDTO user) {
        String username = user.getUsername();
        String jwt = JWTUtil.sign(username, JWTUtil.SECRET);
        Assert.notNull(jwt, "jwt cannot null");
        RBucket rBucket = redissonClient.getBucket(jwt);
        rBucket.set(user);
        return jwt;
    }

    @Override
    public void logout(String jwt) {
        RBucket rBucket = redissonClient.getBucket(jwt);
        rBucket.delete();
    }
}
