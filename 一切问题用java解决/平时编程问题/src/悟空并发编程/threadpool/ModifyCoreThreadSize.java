package 悟空并发编程.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ModifyCoreThreadSize {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5));
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Task1());
            threadPoolExecutor.setCorePoolSize(threadPoolExecutor.getPoolSize() - 1);
//            threadPoolExecutor.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize() - 1);
        }
    }
}

class Task1 implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}