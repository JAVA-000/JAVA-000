package test.gjz.thread;

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
public class ThreadResultDemo2 {

    private static Result result = new Result();

    // 在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程
    public static void main(String[] args) {

        Runnable task = ()->{
            int fiboNumber = Calculator.fibo(30);

            result.setResultCode("200");
            result.setResult(fiboNumber);
        };
        Thread thread = new Thread(task);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程返回码：" + result.getResultCode() + ", 返回结果：" + result.getResult());
    }



}

