package com.unisinsight.demo.cache;

public class LFUStrategy implements ExpireStrategy{

    @Override
    public void get(CacheNode node) {

    }

    @Override
    public void add(CacheNode node) {

    }

    @Override
    public void delete(CacheNode node) {

    }

    @Override
    public <T> CacheNode<T> create(String key, T value, long expireTime) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void destroy() {

    }
}
