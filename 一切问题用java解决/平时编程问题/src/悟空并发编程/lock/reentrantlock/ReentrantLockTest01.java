package 悟空并发编程.lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest01 {
    private static Lock lock = new ReentrantLock();

    public static void method1() {
        lock.lock();
        try {
            // 临界区代码
            System.out.println("method1执行");
            method2();
        } finally {
            lock.unlock();
            System.out.println("method1锁释放");
        }
    }

    public static void method2() {
        lock.lock();
        try {
            // 临界区代码
            System.out.println("method2执行");
        } finally {
            lock.unlock();
            System.out.println("method2锁释放");
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            method1();
        }).start();
    }
}
