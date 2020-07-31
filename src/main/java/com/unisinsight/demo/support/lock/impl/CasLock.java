package com.unisinsight.demo.support.lock.impl;

import java.util.concurrent.atomic.AtomicInteger;

public class CasLock {
    private AtomicInteger serial = new AtomicInteger(0);
    private AtomicInteger lock = new AtomicInteger();
    public void lock(){
        int s = serial.getAndIncrement();
        while (s != lock.get()){}
    }
    public void unlock(){
        lock.incrementAndGet();
    }
}
