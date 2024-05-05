package 悟空并发编程.cas;

import java.util.concurrent.atomic.AtomicInteger;

/*
 *
 * 模拟CAS操作，等价代码
 *
 * */
public class TwoThreadsCompetition implements Runnable {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedVale, int newValue) {
        int oldValue = value;
        if (oldValue == expectedVale) {
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) {
        new AtomicInteger();
        TwoThreadsCompetition r = new TwoThreadsCompetition();
        r.value = 0;
        Thread thread1 = new Thread(r, "线程1");
        Thread thread2 = new Thread(r, "线程2");
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(r.value);
    }

    @Override
    public void run() {
        compareAndSwap(0, 1);
    }
}
