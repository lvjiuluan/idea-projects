package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CinemaReadWrite {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    public static void main(String[] args) {
        new Thread(() -> read(),"Thread-1").start();
        new Thread(() -> read(),"Thread-2").start();
        new Thread(() -> write(),"Thread-3").start();
        new Thread(() -> write(),"Thread-4").start();
    }

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            writeLock.unlock();
        }
    }
}
