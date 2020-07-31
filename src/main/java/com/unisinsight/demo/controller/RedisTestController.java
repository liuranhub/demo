package com.unisinsight.demo.controller;

import com.unisinsight.demo.support.lock.impl.CasLock;
import com.unisinsight.demo.support.lock.impl.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

@RestController
@RequestMapping(value = "/test/redis")
public class RedisTestController {

    @Resource
    private RedisLock redisLock;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    Integer sum  = 0;

    @PostMapping
    public void test(){
//        Lock lock = redisLock.crateLock("testPttl");
//        lock.lock();
//
//        new Thread(()-> {
//                Lock lock2 = redisLock.crateLock("testPttl");
//                lock2.lock();
//                lock2.unlock();
//            }).start();
//        try {
//            Thread.sleep( 10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        lock.unlock();

        CasLock lock = new CasLock();
        sum  = 0;
        CountDownLatch countDownLatch = new CountDownLatch(20);
        for (int i = 0; i < 20 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
//                        synchronized (RedisTestController.class) {
                        for (int i=0; i < 100000 ; i ++) {
                            sum ++;
                        }
//                        }
                    lock.unlock();
                    countDownLatch.countDown();
                }
            }).start();
        }
//
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("sum:"+ sum);
    }

    @GetMapping(value = "/{key}/{param}/{value}")
    public String get(@PathVariable String key, @PathVariable String param, @PathVariable String value){
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                String script = "return redis.call('hmset', KEYS[1], KEYS[2], ARGV[1])";
                Object result = connection.eval(script.getBytes(), ReturnType.VALUE, 2,
                        key.getBytes(), param.getBytes(), value.getBytes() , value.getBytes());
                return result.toString();
            }
        });
    }
}
