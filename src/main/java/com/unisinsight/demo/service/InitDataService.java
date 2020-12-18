package com.unisinsight.demo.service;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 快速找到数据库中丢失编号
 * */
@Component
public class InitDataService<T> {

    @Resource
    private ModelCreateSave<T> modelCreateSave;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(64);

    public void initData(int start, int end){
        BlockingQueue<T> blockingQueue = new ArrayBlockingQueue<>(256);

        Future<?> producer = executorService.submit(()->{
            for (int i=start ;i <= end ;i ++) {

                try {
                    blockingQueue.put(modelCreateSave.create(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer" + Thread.currentThread().getName() + " finish");

        });

        for (int i = 0 ; i < 32; i ++) {
            executorService.submit(() -> {
                while (true) {
                    if (producer.isDone() && blockingQueue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " finish");
                        break;
                    }
                    T t = null;
                    try {
                        t = blockingQueue.take();
                        modelCreateSave.save(t);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return ;
            });
        }
    }
}
