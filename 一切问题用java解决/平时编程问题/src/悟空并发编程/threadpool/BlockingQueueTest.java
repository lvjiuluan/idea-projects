package 悟空并发编程.threadpool;

import java.util.concurrent.*;

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue1 = new ArrayBlockingQueue<Runnable>(100);
        BlockingQueue<Runnable> blockingQueue2 = new LinkedBlockingQueue<>(100);
        BlockingQueue<Runnable> blockingQueue3 = new PriorityBlockingQueue<>();
        BlockingQueue<Runnable> blockingQueue4 = new SynchronousQueue<>();


    }
}
