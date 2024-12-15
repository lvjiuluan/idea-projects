package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class RecursionDemo {
    public static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        accessResource();
        boolean heldByCurrentThread = lock.isHeldByCurrentThread();
        System.out.println(heldByCurrentThread);
    }
    public static void accessResource(){
        lock.lock();
        try {
            System.out.println("已经处理好资源...");
            if(lock.getHoldCount() < 5){
                System.out.println(lock.getHoldCount());
                accessResource();
                System.out.println(lock.getHoldCount());
            }
        }finally {
            lock.unlock();
        }
    }
}
