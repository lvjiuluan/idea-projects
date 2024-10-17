package 悟空并发编程.并发流程控制;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo01 {
    public static void main(String[] args) throws InterruptedException {
        // 构造CountDownLatch对象，5表示要经过5到检测
        CountDownLatch latch = new CountDownLatch(5);
        // 创建固定大小线程池，5表示有5个工人
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable task = new Runnable(){
                @Override
                public void run() {
                    try {
                        // sleep 模拟检测的过程
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("No."+no+"完成了检查。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        // 为什么在finally调用？
                        // 保证await等待的线程能够执行下去，不会一直死等
                        // 检测完成，count减1
                        latch.countDown();
                    }
                }
            };
            // 提交任务
            service.submit(task);
        }
        System.out.println("等待5个人检查完...");
        latch.await(); // main线程会阻塞在这里，直到count减为0
        System.out.println("所有人都完成了工作，进入下一个环节。");
        // 关闭线程池
        service.shutdown();
    }
}
