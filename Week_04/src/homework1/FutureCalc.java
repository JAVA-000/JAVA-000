package src.homework1;

import java.util.concurrent.*;

public class FutureCalc {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        long start = System.currentTimeMillis();
        Future<Integer> result = executor.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return sum();
            }
        });
        executor.shutdown();
        try {
            System.out.println("异步计算结果为：" + result.get());
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