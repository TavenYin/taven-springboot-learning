package com.gitee.taven.aop;

import com.gitee.taven.utils.RedisLock;
import com.gitee.taven.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Aspect
public class RepeatSubmitAspect {

    @Autowired
    private RedisLock redisLock;

    @Pointcut("@annotation(noRepeatSubmit)")
    public void pointCut(NoRepeatSubmit noRepeatSubmit) {
    }

    @Around("pointCut(noRepeatSubmit)")
    public Object before(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) {
        try {
            HttpServletRequest request = RequestUtils.getRequest();
            Assert.notNull(request, "request can not null");

            String token = request.getHeader("");// 此处可以用token或者JSessionId
            String path = request.getServletPath();
            String key = getKey(token, path);
            String clientId = getClientId();
            boolean isSuccess = redisLock.tryLock(key, clientId, 10);

            if (isSuccess) {
                // 获取锁成功, 执行进程
                pjp.proceed();
                // 解锁
                redisLock.releaseLock(key, clientId);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }

}
