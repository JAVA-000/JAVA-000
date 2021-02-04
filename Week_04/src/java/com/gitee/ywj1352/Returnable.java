package com.gitee.ywj1352;

public class Returnable implements Runnable {
    private Object lock;

    public Returnable(Object lock) {
        this.lock = lock;
    }

    private volatile Object obj;

    @Override
    public void run() {
        synchronized (lock) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obj = "返回 结果";
            lock.notify();
        }
    }

    public Object getReturn() {
        return obj;
    }
}
