package practice1_2;

import java.util.concurrent.*;

/**
 * ������ҵ����������˼���ж����ַ�ʽ����main��������һ�����̻߳��̳߳أ�
 * �첽����һ���������õ���������ķ���ֵ���˳����̣߳�
 * д����ķ�����Խ��Խ�ã��ύ��github��
 * <p>
 * Future
 */
public class FutureWay implements Callable<Integer> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(new FutureWay());
        Object result = future.get();//���ǵõ��ķ���ֵ

        // ȷ��  �õ�result �����
        System.out.println("�첽������Ϊ��" + result);

        System.out.println("ʹ��ʱ�䣺" + (System.currentTimeMillis() - start) + " ms");

        // Ȼ���˳�main�߳�
        executor.shutdown();
    }


    @Override
    public Integer call() {
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
