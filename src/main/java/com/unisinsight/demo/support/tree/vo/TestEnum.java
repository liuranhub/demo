package com.unisinsight.demo.support.tree.vo;

public enum TestEnum implements Runnable {
    test;
    @Override
    public void run() {
        System.out.println(this);
    }
    public static class yyy{

    }
}
