package com.zenglinhui.thread;

import java.util.concurrent.*;

/**
 * @author zenglh
 * @date 2020/11/11 14:14
 */
public class Homework04 {

    private final static ExecutorService executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() + 1, 0,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //异步执行
        int result = 0;
        try {
            //result = getResult();
            //result = getResult2();
            result = getResult3();
            //result = getResult4();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        /*int result = sum(36);*/

        System.out.println("异步计算结果为: " + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int getResult() throws InterruptedException, ExecutionException {
        Future<Integer> future = executor.submit(() -> sum(36));
        return future.get();
    }

    private static int getResult2() throws InterruptedException, ExecutionException {
        TaskResult taskResult = new TaskResult();
        Future<TaskResult> future = executor.submit(() -> taskResult.setResult(sum(36)), taskResult);
        return future.get().getResult();
    }

    private static int getResult3() throws InterruptedException, ExecutionException {
        TaskResult taskResult = new TaskResult();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Future<TaskResult> future = executor.submit(() -> {
            taskResult.setResult(sum(36));
            countDownLatch.countDown();
        }, taskResult);
        countDownLatch.await();
        return future.get().getResult();
    }

    private static int getResult4() throws InterruptedException, ExecutionException {
        TaskResult taskResult = new TaskResult();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
        Future<TaskResult> future = executor.submit(() -> {
            try {
                taskResult.setResult(sum(36));
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, taskResult);
        return future.get().getResult();
    }

    private static int sum(int f) {
        return fibo(f);
    }

    private static int fibo(int f) {
        if (f <= 2) {
            return 1;
        }
        int a = 1, b = 1, c;
        for (int i = 3; i <= f; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }


}

class TaskResult {
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}


