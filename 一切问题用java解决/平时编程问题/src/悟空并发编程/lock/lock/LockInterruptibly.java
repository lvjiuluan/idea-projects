package 悟空并发编程.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibly implements Runnable {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new LockInterruptibly(), "线程1");
        Thread thread2 = new Thread(new LockInterruptibly(), "线程2");
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.interrupt();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到了锁");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠期间被中断了");
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "等待锁期间被中断了");
        }
    }
}
