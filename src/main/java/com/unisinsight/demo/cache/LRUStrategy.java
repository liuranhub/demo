package com.unisinsight.demo.cache;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LRUStrategy implements CacheNodeFactory, ExpireStrategy{
    public static final int MAX_CACHE_SIZE = 100;
    private LRUCacheNode first = null;
    private LRUCacheNode last = null;
    private AtomicInteger size = new AtomicInteger(0);

    private Cache cache;

    private Timer timer = new Timer();

    public LRUStrategy(Cache cache){
        this.cache = cache;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               clear();
            }
        }, 5000);
    }

    public void get(CacheNode value){
        unlink((LRUCacheNode) value);
        linkToFirst((LRUCacheNode) value);
    }

    @Override
    public void add(CacheNode value) {
        linkToFirst((LRUCacheNode) value);
    }

    @Override
    public void delete(CacheNode value) {
       unlink((LRUCacheNode) value);
    }

    @Override
    public void clear(){
        while (size.get() > MAX_CACHE_SIZE) {
            cache.delete(last.getKey());
        }
    }

    @Override
    public void destroy(){
        timer.cancel();
    }

    private void linkToFirst(LRUCacheNode node) {
        if (first == null) {
            first = node;
            last = node;
        } else {
            first.next.prev = node;
            node.next = first.next;
            first = node;
        }
        size.incrementAndGet();
    }

    private void unlink(LRUCacheNode node) {
        if (node == first) {
            first = node.next;
        } else {
            node.prev.next = node.next;
            node.prev = null;
        }

        if (node == last) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.next = null;
        }
        size.decrementAndGet();
    }


    public static class LRUCacheNode<T> extends CacheNode<T> {
        private LRUCacheNode<T> prev = null;
        private LRUCacheNode<T> next = null;

        public LRUCacheNode(String key, T value, long expireTime) {
            super(key, value, expireTime);
        }
    }

    public <T> CacheNode<T> create(String key, T value, long expireTime) {
        return new CacheNode<>(key, value, expireTime);
    }

}
