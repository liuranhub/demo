package com.unisinsight.demo.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapCache<T> implements Cache<T>, Lifecycle {

    private ExpireStrategy expireStrategy;

    private TimeExpireProcessor timeExpireProcessor;

    private Map<String, CacheNode<T>> cache;


    public HashMapCache(ExpireStrategyEnum strategy){
        this(new ConcurrentHashMap<>(), strategy);
    }

    public HashMapCache(Map<String, CacheNode<T>> collection, ExpireStrategyEnum strategy){
        this.cache = collection;
        switch (strategy) {
            case LFU:
                this.expireStrategy = new LRUStrategy(this);
                break;
            case LRU:

        }

        this.timeExpireProcessor = new DefaultTimeExpireProcessor(this);
    }

    public void put(String key, T value){
        put(key, value, -1);
    }

    public void put(String key, T value, long expireTime){
        CacheNode<T> node =  cache.get(key);
        // 清理过期key
        if (node !=null && node.isExpire()) {
            delete(key);
        }

        if (node == null) {
            node = expireStrategy.create(key, value, expireTime);
        } else {
            node.setValue(value);
            node.setExpireTime(expireTime);
        }

        cache.put(key, node);
        expireStrategy.add(node);
    }

    public T get(String key) {
        CacheNode<T> node =  cache.get(key);
        if (node == null) {
            return null;
        }

        // 延迟过期
        if (node.isExpire()) {
            delete(key);
            return null;
        }

        expireStrategy.get(node);
        return node.getValue();
    }

    public void delete(String key) {
        expireStrategy.delete(cache.get(key));
        cache.remove(key);
    }

    @Override
    public void destroy() {
        expireStrategy.destroy();
        timeExpireProcessor.destroy();
        expireStrategy = null;
        cache = null;
    }

    @Override
    public Iterator<CacheNode<T>> iterator() {
        return cache.values().iterator();
    }
}
