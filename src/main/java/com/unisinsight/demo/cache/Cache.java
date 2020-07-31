package com.unisinsight.demo.cache;

import java.util.Iterator;

public interface Cache<T> {
    public void put(String key, T value);

    public void put(String key, T value, long expireTime);

    public T get(String key);

    public void delete(String key);

    Iterator<CacheNode<T>> iterator();
}
