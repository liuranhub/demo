package com.unisinsight.demo;

public class Foo {
    private int tryBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;
    public void test() {
        try {
            synchronized (Foo.class) {
                tryBlock = 0;
            }
        } catch(RuntimeException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            catchBlock = 1;
        } finally {
            finallyBlock = 2;
        }
        methodExit = 3;
    }
}
