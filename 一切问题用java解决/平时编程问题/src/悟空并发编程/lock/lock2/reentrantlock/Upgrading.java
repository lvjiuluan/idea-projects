package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Upgrading {
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        new Thread(() -> writeDowngrading(), "Thread-3").start();
        new Thread(() -> writeDowngrading(), "Thread-4").start();
        new Thread(() -> readUpgrading(), "Thread-1").start();
//        new Thread(() -> readUpgrading(), "Thread-2").start();
    }

    private static void readUpgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了读锁，正在读取");
            Thread.sleep(1000);
            System.out.println("升级会带来阻塞");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，升级成功");
            writeLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            readLock.unlock();
        }
    }

    private static void writeDowngrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，正在写入");
            Thread.sleep(1000);
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + "在不释放写锁的情况下，直接获取读锁，成功降级");
            readLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            writeLock.unlock();
        }
    }
}
