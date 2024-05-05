package 悟空并发编程.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class RecursionDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource() {
        lock.lock();
        try {
            System.out.println("已经对资源进行了处理");
            if (lock.getHoldCount() < 5) {
                accessResource();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}
