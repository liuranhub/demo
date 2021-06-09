package com.unisinsight.demo.support.question.aili;

public class Print {
    private int num;
    private static final int MAX_NUM = 10;

    interface Condition {
        boolean condition();
    }

    class PrintThread extends Thread {
        private final Condition condition;

        public PrintThread(Condition condition, String threadName) {
            super(threadName);
            this.condition = condition;
        }

        public void run() {
            // 循环检查
            while (true) {
                // 重点：保证获取变量值和修改变量值是一个原子操作
                synchronized (Print.class) {
                    if (num > MAX_NUM) {
                        break;
                    }
                    if (condition.condition()) {
                        System.out.println(Thread.currentThread().getName() + ":" + num);
                        num++;
                    }
                }
            }
        }
    }

    public void print() {
        new PrintThread(() -> {
            return num % 2 == 1;
        }, "printer1").start();
        new PrintThread(() -> {
            return num % 2 == 0;
        }, "printer2").start();
    }

    public static void main(String[] args) {
        new Print().print();
    }
}
