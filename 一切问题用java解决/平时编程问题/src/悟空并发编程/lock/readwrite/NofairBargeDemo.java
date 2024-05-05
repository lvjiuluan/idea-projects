package 悟空并发编程.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 *
 * 演示非公平和公平的ReentrantReadWriteLock的策略
 *
 * */

public class NofairBargeDemo {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁...");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了读锁，正在读取...");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁...");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，正在写入...");
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(), "线程1").start();
        new Thread(() -> read(), "线程2").start();
        new Thread(() -> read(), "线程3").start();
        new Thread(() -> write(), "线程4").start();
        new Thread(() -> read(), "线程5").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread[] threads = new Thread[1000];
                for (int i = 0; i < 1000; i++) {
                    threads[i] = new Thread(() -> read(), "子线程创建的Thread" + (i + 1));
                }
                for (int i = 0; i < 1000; i++) {
                    threads[i].start();
                }
            }
        }).start();
    }
}
