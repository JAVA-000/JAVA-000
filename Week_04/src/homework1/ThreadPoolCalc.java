package src.homework1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 利用线程池
 */
public class ThreadPoolCalc {

    public static void main(String[] args) {
        AtomicLong result = new AtomicLong();

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                result.set(sum());
            }
        });
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为：" + result.get());
                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
                break;
            }
        }

        System.out.println("End of main thread...");
        // 然后退出main线程
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
