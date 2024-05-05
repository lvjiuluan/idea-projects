package 悟空并发编程.lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CinemaReadWrite {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 同一个锁 readWriteLock 的不同形态
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        new Thread(() -> {
            write();
        }).start();
        new Thread(() -> {
            read();
        }).start();
        new Thread(() -> {
            read();
        }).start();
        new Thread(() -> {
            write();
        }).start();
        new Thread(() -> {
            read();
        }).start();

    }

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了读锁，正在读取...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，正在写入...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
            writeLock.unlock();
        }
    }
}
