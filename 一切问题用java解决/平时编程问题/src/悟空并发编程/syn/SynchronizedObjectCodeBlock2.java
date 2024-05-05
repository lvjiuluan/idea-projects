package 悟空并发编程.syn;

public class SynchronizedObjectCodeBlock2 implements Runnable {
    private static SynchronizedObjectCodeBlock2 instance = new SynchronizedObjectCodeBlock2();
    private final static Object mutex = new Object();
    Object lock1 = new Object();
    Object lock2 = new Object();

    /* @Override
     public void run() {
         System.out.println("我是对象锁的代码块形式，我叫" + Thread.currentThread().getName());
         try {
             Thread.sleep(3000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         System.out.println(Thread.currentThread().getName() + "运行结束");
     }*/
    /*@Override
    public synchronized void run() {
        System.out.println("我是对象锁的代码块形式，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }*/
    @Override
    public void run() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + "获取到了锁1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行结束1");
        }

        synchronized (lock2) {
            System.out.println(Thread.currentThread().getName() + "获取到了锁2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行结束2");
        }
    }

    private static void read() {
        synchronized (SynchronizedObjectCodeBlock2.class) {

        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance, "线程1");
        Thread thread2 = new Thread(instance, "线程2");
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("finished");
    }
}
