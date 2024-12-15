package 悟空并发编程.lock.lock2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MustUnlock {
    private static final Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println("开始执行任务..."+Thread.currentThread().getName());
        }finally {
            lock.unlock();
        }
    }
}
