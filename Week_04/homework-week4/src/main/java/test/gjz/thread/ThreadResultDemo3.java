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
public class ThreadResultDemo3 {

    // 在main函数启动一个新线程，运行一个方法，拿到这 个方法的返回值后，退出主线程
    public static void main(String[] args) {

        Runnable task = new ResultCallBackTask(result -> {
            // 线程完成后回调
            System.out.println("结果码：" + result.getResultCode() + ", 结果：" + result.getResult());
        });
        Thread thread = new Thread(task);
        thread.start();

        System.out.println("主线程退出！");
    }


    /**
     * 结果回调接口
     */
    private interface ResultCallBack{

        /**
         * 成功时回调
         * @param result
         */
        void callBack(Result result);
    }

    /**
     * 带回调的任务
     */
    private static class ResultCallBackTask implements Runnable {

        ResultCallBack resultCallBack;

        public ResultCallBackTask(ResultCallBack resultCallBack) {
            this.resultCallBack = resultCallBack;
        }


        @Override
        public void run() {
            System.out.println("进入线程：" + Thread.currentThread().getName());

            int fiboNumber = Calculator.fibo(30);

            Result result = new Result();
            result.setResultCode("200");
            result.setResult(fiboNumber);
            resultCallBack.callBack(result);
        }
    }



}

