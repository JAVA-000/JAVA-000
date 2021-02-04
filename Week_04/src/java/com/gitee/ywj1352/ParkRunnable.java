package com.gitee.ywj1352;

import java.util.concurrent.locks.LockSupport;

public class ParkRunnable implements Runnable{

    private Thread parent;

    private volatile Object obj;

    public ParkRunnable(Thread parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = "返回 结果";
        LockSupport.unpark(parent);
    }

    public Object getReturn() {
        return obj;
    }
}
