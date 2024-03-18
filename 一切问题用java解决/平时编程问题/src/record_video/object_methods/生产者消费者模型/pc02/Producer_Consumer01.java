package record_video.object_methods.生产者消费者模型.pc02;

import java.util.LinkedList;
import java.util.Queue;

/*
 *
 * 原生的方式来实现生产者和消费者模型
 * 看看会出现什么问题
 * 会出现线程忙等状态！一个线程始终占者cpu时间片，一直在询问
 *
 * 使用wait, notify / notifyAll,解决线程忙等的问题
 * 会出现IllegalMonitorStateException
 * monitor：监视器，我这里理解为锁含义
 *
 * 需要用到synchronized关键字来获取一个对象的monitor
 * */
public class Producer_Consumer01 {
    public static void main(String[] args) {
        Queue queue = new LinkedList();

        int maxSize = 10;

        Producer producer = new Producer(queue, maxSize);
        Consumer consumer = new Consumer(queue, maxSize);

        // 启动线程
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}


class Producer implements Runnable {
    Queue queue;
    int maxSize;

    public Producer(Queue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {

            // 进来的时候先判断满没满，如果满了，就wait，主动进入等待状态
            while (queue.size() == maxSize) {
                queue.notify();
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 只要队列还没达到maxSize，既可以取生产
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.add("包子");
            System.out.println("生产者生产了一个包子，还剩" + queue.size() + "个");

        }
    }
}

class Consumer implements Runnable {
    Queue queue;
    int maxSize;

    public Consumer(Queue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {

            while (queue.isEmpty()) {
                queue.notify();
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 只要判断不为空，就可以进来消费了
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object obj = queue.poll();
            System.out.println("消费者消费了一个" + obj + "，还剩" + queue.size() + "个");
        }
    }
}