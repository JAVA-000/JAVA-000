package com.gitee.ywj1352;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownLatchRunnable implements Runnable{

    private CountDownLatch latch;

    private volatile Object obj;

    public CountDownLatchRunnable(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = "返回 结果";
        latch.countDown();
    }


    public Object getReturn() {
        return obj;
    }


}
