package src.homework1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskCalc {
    public static void main(String[] args) {
        //第一种方式
        long start = System.currentTimeMillis();
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        new Thread(task).start();
        try {
            System.out.println("异步计算结果为：" + task.get());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            System.out.println("Main thread ending...");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

}