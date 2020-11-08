package test.gjz.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
public class ThreadResultDemo4 {

    // 在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程
    public static void main(String[] args) {

        Callable<Result> callable = new CallableTask();
        FutureTask<Result> futureTask = new FutureTask<>(callable);

        new Thread(futureTask).start();

        try {
            Result result = futureTask.get();
            System.out.println("线程返回码：" + result.getResultCode() + ", 线程返回结果：" + result.getResult());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("主线程退出！");
    }


    /**
     * 带回返回值任务
     */
    private static class CallableTask implements Callable<Result> {

        @Override
        public Result call() throws Exception {
            System.out.println("进入线程：" + Thread.currentThread().getName());
            int fiboNumber = Calculator.fibo(30);
            Result result = new Result();
            result.setResultCode("200");
            result.setResult(fiboNumber);
            return result;
        }
    }
}

