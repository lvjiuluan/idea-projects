package 悟空并发编程.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 *
 * 悲观锁：synchronized 和 Lock
 * 乐观锁：原子类 和 并发容器
 *
 * */
public class PessimismOptimismLock {

    int a;

    private static Lock lock = new ReentrantLock();

    public synchronized void testMethod() {
        // 同步代码块
        a++;
    }

    public void testMethod2() {
        lock.lock();
        try {
            // 同步代码块
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }
}
