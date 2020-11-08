package test.gjz.thread;

import java.util.concurrent.*;

/**
 * <pre>
 * 带返回值的线程写法
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/8 13:38
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ThreadResultDemo5 {

    // 在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 可以用其他线程池来代替，主要是使用submit可以接收线程的返回值，如果使用execute，则没有返回值
        Future<Result> resultFuture = executorService.submit(() -> {
            System.out.println("进入线程：" + Thread.currentThread().getName());
            int fiboNumber = Calculator.fibo(30);
            Result result = new Result();
            result.setResultCode("200");
            result.setResult(fiboNumber);
            return result;
        });

        try {
            Result result = resultFuture.get();
            System.out.println("线程返回码：" + result.getResultCode() + "，线程返回结果：" + result.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("主线程结束！！");
    }

}

