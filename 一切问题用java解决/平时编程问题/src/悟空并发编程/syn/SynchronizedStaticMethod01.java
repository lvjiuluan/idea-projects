package 悟空并发编程.syn;

public class SynchronizedStaticMethod01 implements Runnable {
    static SynchronizedStaticMethod01 instance1 = new SynchronizedStaticMethod01();
    static SynchronizedStaticMethod01 instance2 = new SynchronizedStaticMethod01();

    @Override
    public void run() {
        method();
    }

    private static synchronized void method() {
        System.out.println(Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance1, "线程1");
        Thread thread2 = new Thread(instance2, "线程2");
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("finished");
    }
}
