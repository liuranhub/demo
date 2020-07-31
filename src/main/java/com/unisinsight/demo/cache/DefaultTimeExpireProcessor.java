package com.unisinsight.demo.cache;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultTimeExpireProcessor implements TimeExpireProcessor {
    private Cache cache;

    private Timer timer;

    public DefaultTimeExpireProcessor(Cache cache) {
        this.cache = cache;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clear();
            }
        }, 60 * 1000);
    }

    @Override
    public void clear() {
        Iterator<CacheNode> iterator = cache.iterator();
        while (iterator.hasNext()) {
            CacheNode node = iterator.next();
            if (node.isExpire()) {
                cache.delete(node.getKey());
            }
        }
    }

    @Override
    public void destroy() {
        timer.cancel();
    }
}
