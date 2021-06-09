package com.unisinsight.demo.question;

public class FibPrinter {
    private int num = 1;
    private static final int MAX_NUM = 10;

    interface Condition{
        boolean condition();
    }

    class PrintThread extends Thread {
        private final Condition condition;
        public PrintThread(Condition condition, String name){
            super(name);
            this.condition = condition;
        }
        @Override
        public void run() {
            while (true) {
                synchronized (FibPrinter.class) {
                    if(num > MAX_NUM) {
                        break;
                    }
                    if(condition.condition()) {
                        int fibValue = fib(num);
                        System.out.println(Thread.currentThread().getName() + " - " + fibValue);
                        num ++;
                    }
                }
            }
        }

        private int fib(int i) {
            if(i == 0) {
                return 0;
            }
            if(i == 1){
                return 0;
            }

            if(i == 2){
                return 1;
            }

            int first = 0;
            int second = 1;
            for(int index = 3; index <= i ; index ++) {
                int tmp = first + second;
                first = second;
                second = tmp;
            }
            return second;
        }
    }
    public void print(){
        new PrintThread(()->{return num % 3 == 1;}, "1").start();
        new PrintThread(()->{return num % 3 == 2;}, "2").start();
        new PrintThread(()->{return num % 3 == 0;}, "3").start();
    }

    public static void main(String[] args) {
        new FibPrinter().print();
    }
}
