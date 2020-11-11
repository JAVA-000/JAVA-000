package com.gitee.ywj1352;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. 原始的synchronized 和 wait | notify
 * 2. LockSupport park 和 unpark
 * 3. ReentrantLock Condition
 * 4. CountDownLatch
 * 5. Semaphore
 * 6. CyclicBarrier
 * 7. 循环获取
 */
public class WaiteReturnDemo {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws Exception {


        System.out.println("---end---");
    }


    /**
     * 原始的Object lock
     *
     * @throws InterruptedException
     */
    public static void originalLock() throws InterruptedException {
        Object lock = new Object();
        Returnable returnable = new Returnable(lock);
        executorService.submit(returnable);
        synchronized (lock) {
            lock.wait();
        }
        Object aReturn = returnable.getReturn();
        System.out.println(aReturn);
    }

    /**
     * LockSupport park
     */
    public static void parkTest(){
        Thread thread = Thread.currentThread();
        ParkRunnable parkRunnable = new ParkRunnable(thread);
        executorService.submit(parkRunnable);
        LockSupport.park();
        System.out.println(parkRunnable.getReturn());
    }

    public static void reentrantLockTest() throws Exception{
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        JdkLockRunnable jdkLockRunnable = new JdkLockRunnable(condition,reentrantLock);
        executorService.submit(jdkLockRunnable);
        reentrantLock.lock();
        condition.await();
        reentrantLock.unlock();
        System.out.println(jdkLockRunnable.getReturn());
    }

    public static void countDownLatchTest(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        CountDownLatchRunnable runnable = new CountDownLatchRunnable(countDownLatch);
        executorService.submit(runnable);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(runnable.getReturn());
    }

    /**
     *
     * @throws InterruptedException
     */
    public static void semaphoreTest() throws InterruptedException {
        Semaphore semaphor = new Semaphore(1);
        SemaphoreRunnable runnable = new SemaphoreRunnable(semaphor);
        executorService.submit(runnable);
        semaphor.acquire(1);
        System.out.println(runnable.getReturn());
    }

    public static void cyclicBarrierTest(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
            System.out.println("回调");
        });
        CyclicBarrierRunnable cyclicBarrierRunnable = new CyclicBarrierRunnable(cyclicBarrier);

        executorService.submit(cyclicBarrierRunnable);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(cyclicBarrierRunnable.getReturn());
        cyclicBarrier.reset();
    }

    public static void loopTest() throws InterruptedException {
        LoopRunnable loopRunnable = new LoopRunnable();
        executorService.submit(loopRunnable);
        for(;;){
            Object aReturn = loopRunnable.getReturn();
            if (Objects.nonNull(aReturn)){
                System.out.println(aReturn);
                break;
            }
            Thread.sleep(50);
        }
    }

    public static void joinTest() throws InterruptedException {
        JoinThreadRunnable runnable = new JoinThreadRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        Object aReturn = runnable.getReturn();
        System.out.println(aReturn);
    }

}
