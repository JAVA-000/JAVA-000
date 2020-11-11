学习笔记
#### 作业1 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
方法一：将任务利用线程池的shotDown()方法，判断其中线程执行状态
```java
package src.homework1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
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

```

方法二：任务实现Callable接口，然后加入线程池，待线程池全部执行完毕之后，获取其执行结果
```java
package src.homework1;

import java.util.concurrent.Callable;

public class CallAbleSum implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return sum();
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
```

```java
package src.homework1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

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
        // 然后退出main线程
        System.out.println("End of main thread...");
        threadPool.shutdownNow();
    }

}

```

方法三：使用线程的join()方法
```java
package src.homework1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 利用Join
 */
public class JoinCalc {

    public static void main(String[] args) throws InterruptedException {
        AtomicLong result = new AtomicLong();

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result.set(sum());
            }
        });
        thread.start();
        thread.join();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        System.out.println("Main thread ending...");
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

```

方法四：利用FutureTask
```java
package src.homework1;

import java.util.concurrent.*;

public class FutureCalc {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
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
```
方法四：利用FutureTask

#### 作业1 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？