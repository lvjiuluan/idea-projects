package 悟空并发编程.lock.lock2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyTest01 {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = new LockInterruptTask();
        Thread t1 = new Thread(r,"线程1");
        Thread t2 = new Thread(r,"线程2");
        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t1.interrupt();
        t2.interrupt();
    }
}

class LockInterruptTask implements Runnable{

    @Override
    public void run() {
        Lock lock = new ReentrantLock();
        System.out.println(Thread.currentThread().getName()+"尝试获取锁");
        try {
            lock.lockInterruptibly();
            try{
                System.out.println(Thread.currentThread().getName()+"获取到了锁");
                Thread.sleep(5000);
            }catch (InterruptedException e){
                System.out.println(Thread.currentThread().getName()+"睡眠期间被中断了");
            }
            finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"获取锁期间被中断了");
        }
    }
}
