package com.unisinsight.demo.service;

import com.unisinsight.demo.config.Constant;

import javax.annotation.Resource;
import java.util.concurrent.*;

public abstract class AbstractDataService<T> {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Constant.INIT_THREADS * 2);

    public void initData(int start, int end){
        long startTime = System.currentTimeMillis();
        BlockingQueue<T> blockingQueue = new ArrayBlockingQueue<>(256);

        Future<?> producer = executorService.submit(()->{
            for (int i=start ;i <= end ;i ++) {

                try {
                    blockingQueue.put(create(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer" + Thread.currentThread().getName() + " finish");

        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Constant.INIT_THREADS; i ++) {
            executorService.submit(() -> {
                while (true) {
                    if (producer.isDone() && blockingQueue.isEmpty()) {
                        System.out.println("finish : " + (System.currentTimeMillis() - startTime));
                        break;
                    }
                    T t = null;
                    try {
                        t = blockingQueue.take();
                        doing(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return ;
            });
        }
    }

    public abstract T create(int index);
    public abstract void doing(T t);
}
