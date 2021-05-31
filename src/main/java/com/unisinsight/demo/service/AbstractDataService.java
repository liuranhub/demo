package com.unisinsight.demo.service;

import com.unisinsight.demo.config.Constant;
import com.unisinsight.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.concurrent.*;

public abstract class AbstractDataService<T> {
    static final ExecutorService executorService = Executors.newFixedThreadPool(Constant.INIT_THREADS * 2);

    abstract T create(int index);

    abstract void doing(T o);

    public void execute(int start, int end){
        long startTime = System.currentTimeMillis();
        BlockingQueue<T> blockingQueue = new ArrayBlockingQueue<>(1024);

        Future<?> producer = dataProduct(blockingQueue, start, end);

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

    abstract public Future<?> dataProduct(BlockingQueue<T> blockingQueue, int start, int end);
}
