package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 演示读锁插队
 */
public class NofairBargeDemo {
    private static final ReentrantReadWriteLock reentrantReadWriteLock
            = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock readLock
            = reentrantReadWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock
            = reentrantReadWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到写锁，正在读取");
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(), "Thread-1").start();
        new Thread(() -> read(), "Thread-2").start();
        new Thread(() -> read(), "Thread-3").start();
        new Thread(() -> write(), "Thread-4").start();
        new Thread(() -> read(), "Thread-5").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread[] threads = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    threads[i] = new Thread(() -> read(), "main线程创建的Thread-" + i);
                }
                for (int i = 0; i < 1000; i++) {
                    threads[i].start();
                }
            }
        }).start();
    }
}
