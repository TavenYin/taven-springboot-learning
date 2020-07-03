package com.github.taven.listener;

/**
 * 实现该接口 并注册为Spring Bean，即可成为Redis消息监听者
 */
public interface RedisMsgReceiver {

    /**
     * 管道名称
     * @return
     */
    String getChannelName();

    /**
     * 收到消息执行的方法
     * @param message
     */
    void receiveMsg(String message);

}
