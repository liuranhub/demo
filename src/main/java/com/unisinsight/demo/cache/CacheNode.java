package com.unisinsight.demo.cache;

public class CacheNode<T>{
    private String key;
    private T value;
    private long expireEndTime;

    public CacheNode(String key, T value) {
        this(key, value, -1);
    }

    public CacheNode(String key, T value, long expireTime) {
        this.key = key;
        this.value = value;
        setExpireTime(expireTime);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setExpireTime(long expireTime) {
        this.expireEndTime = expireTime >= 0 ?
                System.currentTimeMillis() + expireTime : expireTime;
    }

    public long getExpireTime(){
        return expireEndTime > System.currentTimeMillis() ?
                expireEndTime - System.currentTimeMillis() : 0;
    }

    public boolean isExpire(){
        if (expireEndTime == -1) {
            return false;
        }

        return expireEndTime > System.currentTimeMillis();
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
