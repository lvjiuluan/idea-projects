package 悟空并发编程.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolOOM {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        SubThread subThread = new SubThread();
        for (int i = 0; ; i++) {
            executorService.execute(subThread);
        }
    }
}

class SubThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}