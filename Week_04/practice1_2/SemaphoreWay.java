package practice1_2;

import java.util.concurrent.Semaphore;

/**
 * ������ҵ����������˼���ж����ַ�ʽ����main��������һ�����̻߳��̳߳أ�
 * �첽����һ���������õ���������ķ���ֵ���˳����̣߳�
 * д����ķ�����Խ��Խ�ã��ύ��github��
 * <p>
 * SemaphoreWay
 */
public class SemaphoreWay {

    private static volatile Integer result = null;
    private final Semaphore semaphore = new Semaphore(1);

    public void semaphoreSum() throws InterruptedException {
        semaphore.acquire();
        result = sum();
        semaphore.release();
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
        Thread thread = new Thread(() -> {
            try {
                new SemaphoreWay().semaphoreSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        //���ǵõ��ķ���ֵ

        // ȷ��  �õ�result �����
        System.out.println("�첽������Ϊ��" + result);

        System.out.println("ʹ��ʱ�䣺" + (System.currentTimeMillis() - start) + " ms");

        // Ȼ���˳�main�߳�
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
