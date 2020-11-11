package practice1_2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ������ҵ����������˼���ж����ַ�ʽ����main��������һ�����̻߳��̳߳أ�
 * �첽����һ���������õ���������ķ���ֵ���˳����̣߳�
 * д����ķ�����Խ��Խ�ã��ύ��github��
 * <p>
 * LockConditionWay
 */
public class LockConditionWay {

    private static Integer result = null;
    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void lockSum() {
        lock.lock();
        try {
            result = sum();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // �����ﴴ��һ���̻߳��̳߳أ�
        // �첽ִ�� ���淽��
        Thread thread = new Thread(() -> new LockConditionWay().lockSum());
        thread.start();
        thread.join();

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
