package com.unisinsight.demo.cache;

public interface CacheNodeFactory {
    public <T> CacheNode<T> create(String key, T value, long expireTime);
}
