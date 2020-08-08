package com.unisinsight.demo.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorCompletionService{

    private ExecutorService executorService;

    public ExecutorCompletionService(int size){
        this.executorService = Executors.newFixedThreadPool(size);
    }

    public ExecutorCompletionService(ExecutorService executorService){
        this.executorService = executorService;
    }

    public <T> BlockFuture<T> submit(List<Callable<T>> list) {
        BlockFuture<T> blockFuture = new BlockFuture<>(list.size());
        for (Callable c : list) {
            executorService.submit(new MyRunnable(c, blockFuture));
        }
        return blockFuture;
    }

    public class BlockFuture<T> implements Future<T> {
        private AtomicInteger state;
        private BlockingDeque<T> blockingDeque;
        public BlockFuture(int count){
            this.state = new AtomicInteger(count);
            this.blockingDeque = new LinkedBlockingDeque<>();
        }
        public synchronized void push(T t) {
            int value = state.decrementAndGet();
            blockingDeque.push(t);
            if (value == 0) {
                notifyAll();
            }
        }

        public synchronized boolean isFinish(){
            if (state.get() == 0) {
                return true;
            }

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return state.get() == 0 && blockingDeque.size() == 0;
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            return blockingDeque.take();
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    public class MyRunnable<T> implements Runnable {
        private Callable<T> callable;

        private BlockFuture<T> future;

        public MyRunnable(Callable<T> c, BlockFuture<T> future){
            this.callable = c;
            this.future = future;
        }

        @Override
        public void run() {
            try {
                T t = callable.call();
                future.push(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown(){
        executorService.shutdown();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorCompletionService service = new ExecutorCompletionService(4);
        List<Callable<String>> list = new ArrayList<>();

        for (int i = 0 ;i < 10 ;i ++) {
            final int index = i;
            Callable<String> callable = ()->{
                int sleep = ThreadLocalRandom.current().nextInt(100, 500);
                Thread.sleep(sleep);
                return index + " : " + "hello world, sleep " + sleep ;
            };

            list.add(callable);
        }

        Future<String> blockFuture = service.submit(list);

        while (!blockFuture.isDone()) {
            System.out.println(blockFuture.get());
        }
        service.shutdown();
    }
}
