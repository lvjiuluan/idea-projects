package 悟空并发编程.并发流程控制;

import java.util.concurrent.Executors;

public class EveryTaskOneThread {
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.start();
        Executors.newFixedThreadPool(1);
    }

    static class Task implements Runnable{
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
