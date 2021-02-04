package com.gitee.ywj1352;

public class JoinThreadRunnable implements Runnable {
    private volatile Object obj;

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        obj = "返回的结果";
    }

    public Object getReturn() {
        return obj;
    }

}
