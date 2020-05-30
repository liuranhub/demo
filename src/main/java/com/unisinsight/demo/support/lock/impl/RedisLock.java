package com.unisinsight.demo.support.lock.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RedisLock {
    private static final Integer PTTL = 60 * 1000 ;
    private static final Integer RENEWAL_TIME = (int)(PTTL * 0.3);
    public static final String UNLOCK_MESSAGE = "unlockMessage";
    private static Map<String, Object> notify = new ConcurrentHashMap<>();
    private static Lock notifyLock = new ReentrantLock();
    private static Map<String, Condition> notifyCondition = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    public class LockImpl implements Lock {

        private String lockId;
        private String threadId;
        private Timer timer = null;

        private LockImpl(String lockId) {
            this.lockId = lockId;
            threadId = UUID.randomUUID().toString();
        }

        @Override
        public void lock() {
            while (true) {
                RedisCallback<Long> redisCallbackLock = new RedisCallLock(lockId, threadId);
                Long result = redisTemplate.execute(redisCallbackLock);
                if (result < 0) {
                    createWatchDog();
//                    System.out.println(lockId + ":获取锁成功");
                    return;
                }
                wait(lockId);
            }
        }

        @Override
        public void lockInterruptibly() {

        }

        @Override
        public boolean tryLock() {
            return tryLock(1, TimeUnit.SECONDS);
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) {
            final long deadline = System.nanoTime() + unit.toNanos(time);
            while (true) {
                RedisCallback<Long> lock = new RedisCallLock(lockId, threadId);
                Long result = redisTemplate.execute(lock);
                if (result < 0) {
                    createWatchDog();
//                    System.out.println(lockId + ":获取锁成功");
                    return true;
                }

                wait(lockId, 100L);

                if (System.currentTimeMillis() > deadline) {
                    return false;
                }
            }
        }

        @Override
        public void unlock() {
            RedisCallback<Long> unlock = new RedisCallUnlock(lockId, threadId);
            Long result = redisTemplate.execute(unlock);
            if (result == 0) {
//                System.out.println(lockId + ":释放重入锁");
            }
            if (result == 1) {
                removeFromWatchDog();
//                System.out.println(lockId + ":释放锁成功");
            }
            if (result == -1) {
//                System.out.println(lockId + ":释放锁失败")
            }

        }

        /**
         * 锁自动续期
         * */
        private void createWatchDog(){
            if (timer != null) {
                return;
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    RedisCallback<Long> unlock = new RedisCallUpdatePttl(lockId, threadId);
                    redisTemplate.execute(unlock);
                }
            }, RENEWAL_TIME, RENEWAL_TIME);
        }

        private void removeFromWatchDog() {
            if (timer != null) {
                timer.cancel();
            }
        }

        @Override
        public Condition newCondition() {
            return null;
        }

        private void wait(String lockId) {
//            waitUseCondition(lockId, -1);
            waitUseObject(lockId, -1);
        }

        private void wait(String lockId, long time) {
//            waitUseCondition(lockId, time);
            waitUseObject(lockId, time);
        }

        private void waitUseObject(String lockId, long time) {
            notify.putIfAbsent(lockId,  new Object());
            Object lock = notify.get(lockId);
            synchronized (lock) {
                try {
                    if (time > 0) {
                        lock.wait(time);
                    } else {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void waitUseCondition(String lockId, long time) {
            notifyCondition.putIfAbsent(lockId, notifyLock.newCondition());
            Condition condition = notifyCondition.get(lockId);
            synchronized (condition){
                try {
                    if (time < 0) {
                        condition.await();
                    } else {
                        condition.await(time, TimeUnit.NANOSECONDS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void notify(String lockId){
//        notifyUseCondition(lockId);
        notifyUseObject(lockId);
    }

    private static void notifyUseObject(String lockId) {
        Object lock = notify.get(lockId);
        if (lock != null) {
            synchronized (lock){
                notify.get(lockId).notifyAll();
            }
        }
        //notify.remove(lockId);
    }

    private static void notifyUseCondition(String lockId){
        Condition condition = notifyCondition.get(lockId);
        synchronized (condition){
            condition.notify();
        }
    }

    public synchronized Lock crateLock(String lockId) {
        return new LockImpl(lockId);
    }

    private class RedisCallUpdatePttl implements RedisCallback<Long> {
        private String lockId;
        private String threadId;
        private RedisCallUpdatePttl(String lockId, String threadId) {
            this.lockId = lockId;
            this.threadId = threadId;
        }

        @Override
        public Long doInRedis(@Nullable RedisConnection connection) throws DataAccessException {
            String script =
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                    "return 1; " +
                    "end; " +
                    // 没有获取到锁，返回过期时间
                    "return -1;";

            List<Long> result = null;
            try {
                result = connection.eval(script.getBytes(),ReturnType.MULTI, 1,
                        lockId.getBytes(),  PTTL.toString().getBytes(), threadId.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (CollectionUtils.isEmpty(result)) {
                return 0L;
            }
            return result.get(0);
        }
    }

    private class RedisCallLock implements RedisCallback<Long> {
        private String lockId;
        private String threadId;
        private RedisCallLock(String lockId, String threadId) {
            this.lockId = lockId;
            this.threadId = threadId;
        }

        /**
         * @return
         * -1:获取锁成功
         * -2:获取重入锁成功
         * >0:锁已被抢占，返回锁过期时间
         * */
        @Override
        public Long doInRedis(@Nullable RedisConnection connection) throws DataAccessException {
            String script =
                    // 首次获取锁
                    "if (redis.call('exists', KEYS[1]) == 0) then " +
                        "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return -1; " +
                    "end; " +
                    //重入锁
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return -2; " +
                    "end; " +
                    // 没有获取到锁，返回过期时间
                    "return redis.call('pttl', KEYS[1]);";
            List<Long> result = null;
            try {
                result = connection.eval(script.getBytes(),ReturnType.MULTI, 1,
                        lockId.getBytes(),  PTTL.toString().getBytes(), threadId.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (CollectionUtils.isEmpty(result)) {
                return 0L;
            }
            return result.get(0);
        }
    }

    private class RedisCallUnlock implements RedisCallback<Long>{
        private String lockId;
        private String threadId;
        private RedisCallUnlock(String lockId, String threadId) {
            this.lockId = lockId;
            this.threadId = threadId;
        }

        /**
         * @return
         * 0:释放重入锁成功，不能被其它线程抢占；
         * 1:释放锁成功，可以被其它线程抢占；
         * -1:释放锁失败
         * */
        @Override
        public Long doInRedis(@Nullable RedisConnection connection) throws DataAccessException {
            String script =
                    // 锁已经被释放
                    "if (redis.call('exists', KEYS[1]) == 0) then " +
                        "redis.call('publish', KEYS[2], KEYS[1]); " +
                        "return 1; " +
                    "end;" +
                    // 获取锁的非本线程 : 锁名 + 获取线程ID
                    "if (redis.call('hexists', KEYS[1], ARGV[2]) == 0) then " +
                        "return -1;" +
                    "end; " +
                    // 释放重入锁
                    "local counter = redis.call('hincrby', KEYS[1], ARGV[2], -1); " +
                    "if (counter > 0) then " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return 0; " +
                    // 释放普通锁
                    "else " +
                        "redis.call('del', KEYS[1]); " +
                        "redis.call('publish', KEYS[2], KEYS[1]); " +
                        "return 1; "+
                    "end; " +
                        "return -1;";
            List<Long> result = null;
            try {
                result = connection.eval(script.getBytes(), ReturnType.MULTI, 2,
                        lockId.getBytes(), UNLOCK_MESSAGE.getBytes(),
                        PTTL.toString().getBytes(), threadId.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (CollectionUtils.isEmpty(result)) {
                return 0L;
            }
            return result.get(0);
        }
    }
}
