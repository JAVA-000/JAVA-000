package practice1_2;

import java.util.concurrent.*;

/**
 * ������ҵ����������˼���ж����ַ�ʽ����main��������һ�����̻߳��̳߳أ�
 * �첽����һ���������õ���������ķ���ֵ���˳����̣߳�
 * д����ķ�����Խ��Խ�ã��ύ��github��
 * <p>
 * FutureTask
 */
public class FutureTaskWay {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
        ExecutorService executor = Executors.newSingleThreadExecutor();

        FutureTask<Integer> futureTask = new FutureTask<>(FutureTaskWay::sum);
        executor.submit(futureTask);
        Integer result = futureTask.get();//���ǵõ��ķ���ֵ

        // ȷ��  �õ�result �����
        System.out.println("�첽������Ϊ��" + result);

        System.out.println("ʹ��ʱ�䣺" + (System.currentTimeMillis() - start) + " ms");

        // Ȼ���˳�main�߳�
        executor.shutdown();
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
