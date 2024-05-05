package 悟空并发编程.lock.reentrantlock;


import java.util.concurrent.locks.ReentrantLock;

/*
 * 演示多线程预定电影院作为
 *
 * */
public class CinemaBookSeat {
    private static ReentrantLock lock = new ReentrantLock();

    private static void bookSeat() {
        lock.lock();
        try {
            // 临界区代码块
            System.out.println(Thread.currentThread().getName() + "开始预定座位...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "完成预定座位...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int threadCount = 4;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                bookSeat();
            }, "线程" + (i + 1));
            threads[i].start();
        }
    }
}
