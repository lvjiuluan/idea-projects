package 悟空并发编程.并发流程控制;

import java.util.concurrent.Executors;

public class ForLoop {
    public static void main(String[] args) {

        Executors.newFixedThreadPool(1);
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    static class Task implements Runnable{
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
