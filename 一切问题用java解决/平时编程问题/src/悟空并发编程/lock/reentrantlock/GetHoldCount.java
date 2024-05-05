package 悟空并发编程.lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class GetHoldCount {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println(lock.getHoldCount());
        lock.lock();
        System.out.println(lock.getHoldCount());
        try {
            lock.lock();
            System.out.println(lock.getHoldCount());
            try {
                lock.lock();
                System.out.println(lock.getHoldCount());
                try {

                } finally {
                    lock.unlock();
                    System.out.println(lock.getHoldCount());
                }
            } finally {
                lock.unlock();
                System.out.println(lock.getHoldCount());
            }
        } finally {
            lock.unlock();
            System.out.println(lock.getHoldCount());
        }
    }
}
