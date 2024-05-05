package 韩顺平Java.并发编程;

public class DeadLock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new Thread(new A(lock1,lock2),"线程A").start();
        new Thread(new B(lock1,lock2),"线程B").start();
    }
}

class A implements Runnable {

    final private Object lock1;
    final private Object lock2;

    public A(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        // 先拿第一个锁，再拿第二个锁
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + "拿到了第一个锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "拿到了第二个锁");
            }
        }
    }
}

class B implements Runnable {

    final private Object lock1;
    final private Object lock2;

    public B(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        // 先拿第二个锁，再拿第一个锁
        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + "拿到了第二个锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "拿到了第一个锁");
            }
        }
    }
}