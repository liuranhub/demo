package com.unisinsight.demo.cache;

public interface ExpireStrategy extends CacheNodeFactory, Lifecycle{
    public void get(CacheNode node);

    public void add(CacheNode node);

    public void delete(CacheNode node);

    public void clear();

}
