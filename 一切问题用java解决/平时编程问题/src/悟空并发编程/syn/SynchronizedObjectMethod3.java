package 悟空并发编程.syn;

public class SynchronizedObjectMethod3 implements Runnable {

    static SynchronizedObjectMethod3 instance = new SynchronizedObjectMethod3();

    @Override
    public void run() {
        methd();
    }

    private synchronized void methd() {
        System.out.println(Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
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
