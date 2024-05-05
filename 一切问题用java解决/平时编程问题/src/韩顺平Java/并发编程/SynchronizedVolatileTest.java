package 韩顺平Java.并发编程;

public class SynchronizedVolatileTest {
    public volatile int race = 0;

    public int getRace() {
        return race;
    }

    public synchronized void increase() {
        race++;
    }

    public static void main(String[] args) {
        SynchronizedVolatileTest volatileTest = new SynchronizedVolatileTest();
        int threadCount = 5;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        volatileTest.increase();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行10000次++后，race值为" + volatileTest.getRace());
                }
            }, "线程" + (i + 1));
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("累加结果" + volatileTest.getRace());
    }
}
