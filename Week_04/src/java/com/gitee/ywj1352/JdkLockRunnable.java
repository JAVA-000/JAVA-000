package com.gitee.ywj1352;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JdkLockRunnable implements Runnable {

    private ReentrantLock lock;

    private Condition condition;

    private volatile Object obj;

    public JdkLockRunnable(Condition condition, ReentrantLock lock) {
        this.condition = condition;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        obj = "返回 结果";
        condition.signal();
        lock.unlock();
    }


    public Object getReturn() {
        return obj;
    }

}
