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
public class ThreadResultDemo1 {

    // 在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程
    public static void main(String[] args) {
        Result result = new Result();
        Runnable task = new ResultThreadTask(result);
        Thread thread = new Thread(task);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程返回码：" + result.getResultCode() + ", 返回结果：" + result.getResult());
    }


    /**
     * 带返回值的task
     */
    private static class ResultThreadTask implements Runnable{

        private Result result;


        public ResultThreadTask(Result result) {
            this.result = result;
        }

        @Override
        public void run() {
            int fiboNumber = Calculator.fibo(30);

            result.setResultCode("200");
            result.setResult(fiboNumber);
        }
    }



}

