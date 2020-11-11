package src.homework1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 利用线程池+Callable
 */
public class ThreadPoolCallableInvokeCalc {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        List<CallAbleSum> futureList = new ArrayList<CallAbleSum>();
        futureList.add(new CallAbleSum());
        List<Future<Integer>> futures = threadPool.invokeAll(futureList);
        Future<Integer> integerFuture = futures.get(0);
        System.out.println("异步计算结果为：" + integerFuture.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        System.out.println("End of main thread...");
        threadPool.shutdownNow();
        // 然后退出main线程
    }


}
