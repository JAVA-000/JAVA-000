package com.gitee.ywj1352;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunnable implements Runnable{

    private CyclicBarrier cb;

    private volatile Object obj;

    public CyclicBarrierRunnable( CyclicBarrier cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = "返回 结果";
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


    public Object getReturn() {
        return obj;
    }

}
