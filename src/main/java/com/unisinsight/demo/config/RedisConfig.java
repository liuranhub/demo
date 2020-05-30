package com.unisinsight.demo.config;

import com.unisinsight.demo.support.lock.impl.RedisLock;
import com.unisinsight.demo.support.lock.impl.SubscribeListener;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@SpringBootConfiguration
public class RedisConfig {
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // 添加订阅者监听类，数量不限.PatternTopic定义监听主题,这里监听test-topic主题
        container.addMessageListener(new SubscribeListener(), new PatternTopic(RedisLock.UNLOCK_MESSAGE));
        return container;
    }
}
