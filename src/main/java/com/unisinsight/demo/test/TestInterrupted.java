package com.unisinsight.demo.test;

import com.unisinsight.demo.support.tree.vo.Menu;
import com.unisinsight.demo.support.tree.vo.TestEnum;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestInterrupted {
    static Lock lock = new ReentrantLock();

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Test
    public void testString(){
        int out = 0;
        int base = 2;
        int value =512;
        for (int i = value; (i = i >> base) != 0; out ++);
        System.out.println(out);

    }

    @Test
    public void testInnerClass(){
        Menu menu = new Menu();
        menu.setMenuCode("tttt");
        menu.setLength(12);
        try {
            Menu clone = menu.clone();
            clone.setMenuCode("zzz");
            clone.updateValue(2,111);

            System.out.println(clone.getMenuCode());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEnum(){
        TestEnum.yyy y = new TestEnum.yyy();
        executorService.submit(TestEnum.test);
    }

    @Test
    public void testForEach() throws ExecutionException, InterruptedException {

//        List<String> r = Arrays
//                .stream(System.getProperty("sun.boot.class.path").split(";"))
//                .collect(Collectors.toList());
//        Arrays.stream(System.getProperty("sun.boot.class.path").split(";"));
//        Map<String, String> map = new HashMapCache<>(16);

        Future<String> future = executorService.submit(()->{
            AtomicInteger index = new AtomicInteger();
            Arrays.stream(System.getProperty("sun.boot.class.path").split(";"))
                    .filter((x)-> x.endsWith("jar"))
                    .limit(10)
                    .forEach((x)->{
                        System.out.println(index.incrementAndGet() + ":" + x);
                    });
            return "success";
        });

        future.get();
    }


    @Test
    public void completableFutureTest() {
        CompletableFuture<Object> completableFuture1 = CompletableFuture
                .supplyAsync(()-> "hello" , executorService).thenApply((x) -> x + " word");

        System.out.println(completableFuture1.join());

        executorService.shutdown();
    }

    private static class Student{
        private String name;
        public Student(String name) {
            this.name = name;
        }
//        @Override
//        public int hashCode() {
//            return name == null ? 0 : name.hashCode();
//        }
    }
}
