package com.gitee.ywj1352;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SemaphoreRunnable implements Runnable{

    private Semaphore semaphore;

    private volatile Object obj;

    public SemaphoreRunnable(Semaphore semaphore) {
        this.semaphore = semaphore;
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = "返回 结果";
        semaphore.release();
    }


    public Object getReturn() {
        return obj;
    }
}
