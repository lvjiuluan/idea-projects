package 悟空并发编程.lock.lock;

public class SynchronizedInterrupt {
    public static synchronized void read() {
        System.out.println(Thread.currentThread().getName() + "获取到了synchronized锁");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "睡眠被打断");
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> read(), "线程1");
        Thread thread2 = new Thread(() -> read(), "线程2");
        thread1.start();
        thread2.start();
        thread2.interrupt();
    }
}
