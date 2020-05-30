package com.unisinsight.demo.support.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Test {
    public static void main(String[] args) {
        Integer oldCapacity = 9;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity);

//        List<Integer> list = new LinkedList<>();
//        long start = System.currentTimeMillis();
//        for (int i=0;i < 100000 ;i++) {
//            list.add(i);
//        }
//        System.out.println(System.currentTimeMillis() - start);
//        start = System.currentTimeMillis();
//
//        for (int i =0;i < list.size();i ++) {
//            list.get(i);
//        }
//
//        System.out.println(System.currentTimeMillis() - start);
//        start = System.currentTimeMillis();
//
//        for (Integer value : list) {
//
//        }
//        System.out.println(System.currentTimeMillis() - start);
//
//
//        CyclicBarrier barrier = new CyclicBarrier(2);
//        for (int i =0;i < 2 ;i ++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        barrier.await();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (BrokenBarrierException e) {
//                        e.printStackTrace();
//                    }
//
//                    for (Integer i = 0 ;i < list.size(); i++) {
//                        list.remove(i);
//                    }
//
////                    Iterator<Integer> it = list.iterator();
////                    while (it.hasNext()) {
////                        it.next();
////                        it.remove();
////                    }
//                }
//            }).start();
//        }
    }
}
