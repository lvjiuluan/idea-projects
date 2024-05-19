package 悟空并发编程.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 *   演示关闭线程池
 *
 * */
public class ShuntDown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ShuntDownTask());
        }
        Thread.sleep(1500);
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println(runnables.size());
        executorService.shutdown();
        boolean b = executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
//        System.out.println(executorService.awaitTermination(3, TimeUnit.SECONDS));

//        System.out.println(executorService.isShutdown());
//        System.out.println(executorService.isTerminated());
//        executorService.shutdown();
//        System.out.println(executorService.isShutdown());
//        System.out.println(executorService.isTerminated());
    }
}

class ShuntDownTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }
}