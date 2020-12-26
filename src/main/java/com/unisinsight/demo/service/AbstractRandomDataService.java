package com.unisinsight.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public abstract class AbstractRandomDataService<T> extends AbstractDataService<T> {
    @Override
    public Future<?> dataProduct(BlockingQueue<T> blockingQueue, int start, int end) {
        List<Integer> order = new ArrayList<>(start);
        for (int i = 1; i <= end; i ++) {
            order.add(i);
        }
        Collections.shuffle(order);

        return executorService.submit(()->{
            for (int index : order) {
                try {
                    blockingQueue.put(create(index));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
