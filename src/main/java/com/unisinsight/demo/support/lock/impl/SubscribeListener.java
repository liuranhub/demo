package com.unisinsight.demo.support.lock.impl;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class SubscribeListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisLock.notify(new String(message.getBody()));
    }
}
