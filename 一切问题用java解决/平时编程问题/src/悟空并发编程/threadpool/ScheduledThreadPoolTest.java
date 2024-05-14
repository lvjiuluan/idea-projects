package 悟空并发编程.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
//        executorService.schedule(new Task(), 5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS);
    }
}
