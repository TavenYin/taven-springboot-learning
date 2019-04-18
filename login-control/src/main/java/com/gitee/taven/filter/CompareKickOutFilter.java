package com.gitee.taven.filter;

import com.gitee.taven.service.UserService;
import com.gitee.taven.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CompareKickOutFilter extends KickOutFilter {

    @Autowired
    private UserService userService;

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        String lockKey = PREFIX_LOCK + username;
        String userKey = PREFIX + username;

        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(5, SECONDS);

        try {
            RBucket<String> bucket = redissonClient.getBucket(userKey);
            String redisToken = bucket.get();

            if (token.equals(redisToken)) {
                return true;

            } else if (StringUtils.isBlank(redisToken)) {
                bucket.set(token);

            } else {
                Long redisTokenUnixTime = JWTUtil.getClaim(redisToken, "createTime").asLong();
                Long tokenUnixTime = JWTUtil.getClaim(token, "createTime").asLong();

                // token > redisToken 则覆盖
                if (tokenUnixTime.compareTo(redisTokenUnixTime) > 0) {
                    bucket.set(token);

                } else {
                    // 注销当前token
                    userService.logout(token);
                    sendResponse(response, 401, "您的账号已在其他设备登录");
                }

            }

        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                LOGGER.info(Thread.currentThread().getName() + " unlock");

            } else {
                LOGGER.info(Thread.currentThread().getName() + " already automatically release lock");
            }
        }

        return false;

    }
}
