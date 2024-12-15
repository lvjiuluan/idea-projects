package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  演示多线程预定电影院座位
 */
public class CinemaBookSeat {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
        new Thread(()->bookSeat()).start();
    }

    private static void bookSeat(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预定座位");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "完成预定座位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
