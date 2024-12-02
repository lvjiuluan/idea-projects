package 悟空并发编程.threadpool02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示固定线程池 OOM 的情况
 * PS：需要调整idea的运行参数
 */
public class FixedThreadPoolOOM {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        for (int i = 0;; i++) {
            executorService.execute(new SubThread());
        }
    }
}
class SubThread implements Runnable{

    @Override
    public void run() {
        try {
            // 这里为什么要休眠这么长时间呢？
            // 因为我们想演示newFixedThreadPool使用无界队列
            // 导致 OOM 的情况，所以我们让一个线程不断的处于执行中
            // 后面来的线程就会持续放入LinkedBlockingQueue队列中
            // 直到内存耗尽
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}