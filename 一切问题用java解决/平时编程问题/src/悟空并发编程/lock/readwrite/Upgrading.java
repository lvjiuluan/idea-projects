package 悟空并发编程.lock.readwrite;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Upgrading {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    // 同一个锁 readWriteLock 的不同形态
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        new AtomicInteger();
        System.out.println("先演示降级是可以的");
        new Thread(() -> writeDowngrading()).start();
        System.out.println("演示升级是不行的");
        new Thread(() -> readUpgrading()).start();
    }

    private static void readUpgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了读锁，正在读取...");
            try {
                Thread.sleep(1000);
                System.out.println("升级会带来阻塞");
                writeLock.lock();
                System.out.println("升级成功，获取到了写锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了读锁");
            readLock.unlock();
        }
    }

    private static void writeDowngrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，正在写入...");
            try {
                Thread.sleep(1000);
                readLock.lock();
                System.out.println("在不释放写锁的情况下，直接获取读锁，成功降级");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了写锁");
            writeLock.unlock();
        }
    }
}
