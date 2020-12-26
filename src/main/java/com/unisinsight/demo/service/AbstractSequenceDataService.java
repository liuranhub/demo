package com.unisinsight.demo.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public abstract class AbstractSequenceDataService<T> extends AbstractDataService<T> {
    @Override
    public Future<?> dataProduct(BlockingQueue<T> blockingQueue, int start, int end) {
        return executorService.submit(()->{
            for (int i=start ;i <= end ;i ++) {

                try {
                    blockingQueue.put(create(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer" + Thread.currentThread().getName() + " finish");

        });
    }
}
