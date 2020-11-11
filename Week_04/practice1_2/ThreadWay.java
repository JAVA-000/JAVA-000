package practice1_2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ������ҵ����������˼���ж����ַ�ʽ����main��������һ�����̻߳��̳߳أ�
 * �첽����һ���������õ���������ķ���ֵ���˳����̣߳�
 * д����ķ�����Խ��Խ�ã��ύ��github��
 * <p>
 * ThreadWay
 */
public class ThreadWay {

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
        AtomicInteger value = new AtomicInteger();
        Thread thread = new Thread(() -> value.set(sum()));
        thread.start();
        thread.join();

        int result = value.get();//���ǵõ��ķ���ֵ

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
