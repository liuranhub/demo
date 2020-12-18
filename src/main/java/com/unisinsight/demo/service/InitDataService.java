package com.unisinsight.demo.service;

import com.unisinsight.demo.config.Constant;
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

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Constant.INIT_THREADS * 2);

    public void initData(int start, int end){
        long startTime = System.currentTimeMillis();
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
