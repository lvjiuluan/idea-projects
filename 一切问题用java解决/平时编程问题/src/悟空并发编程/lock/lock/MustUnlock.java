package 悟空并发编程.lock.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 由于Lock接口不会像synchronized关键字那样，在发生异常的时候由JVM自动释放锁，而是需要
 * 我们进行手动是否，所以需要在try-catch-finally代码块里面进行Lock的加锁与释放，
 * 在finally里面释放锁，保证Lock锁总能被释放
 *
 * */
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void readFile() {
        lock.lock();
        try {
            // 获取本锁保护的资源
            System.out.println(Thread.currentThread().getName() + "开始执行任务");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void readFile2(){
        boolean b = lock.tryLock();
        System.out.println(b);
    }

    public static void readFile3(){
        boolean b = false;
        try {
            b = lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(b);
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(()->{
           readFile();
        });
        Thread thread2 = new Thread(()->{
            readFile2();
        });
        Thread thread3 = new Thread(()->{
            readFile3();
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
