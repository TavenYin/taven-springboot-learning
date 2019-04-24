package com.gitee.taven.filter;

import com.gitee.taven.pojo.UserBO;
import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 先标记后踢出
 *
 */
public class QueueKickOutFilter extends KickOutFilter {
    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("Authorization");
        UserBO currentSession = getSession(token);
        Assert.notNull(currentSession, "currentSession cannot null");
        String userKey = PREFIX + "deque_" + currentSession.getUsername();

        //TODO 同步控制
        RDeque<String> deque = redissonClient.getDeque(userKey);

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(token) && currentSession.isKickout() == false) {
            deque.push(token);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            String kickoutSessionId;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }

            try {
                UserBO kickoutSession = getSession(kickoutSessionId);
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setKickout(true);
                    storage(kickoutSession, kickoutSessionId);
                }

            } catch (Exception e) {}

        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (currentSession.isKickout()) {
            //会话被踢出了
            try {
                // 注销
                userService.logout(token);
                // 向前端发送一个码
                sendJsonResponse(response, 4001, "您的账号已在其他设备登录");

            } catch (Exception e) {
            }

            return false;
        }

        return true;
    }

    private UserBO getSession(String key) {
        RBucket<UserBO> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    private void storage(UserBO session, String sessionId) {
        RBucket<UserBO> bucket = redissonClient.getBucket(sessionId);
        bucket.set(session);
    }

}
