package pre.生产者消费者模型.pc03;

import java.util.LinkedList;
import java.util.Queue;

/*
 *
 * 使用wait，notify解决线程忙等问题
 * 如果不适用synchronized关键字获取该对象的监视器（我这里理解为锁）
 * 就会报IllegalMonitorStateException
 * 看来必须使用synchoronized
 *
 * */
public class Producer_Consumer02 {
    public static void main(String[] args) {
        Queue queue = new LinkedList();

        int maxSize = 10;

        Producer producer = new Producer(queue, maxSize);
        Consumer consumer = new Consumer(queue, maxSize);

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
            synchronized (queue) {
                // 写一个循环判断是否为空， 如果为空就让该线程等待
                while (queue.isEmpty()) {
                    queue.notify();
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Object obj = queue.poll();
                System.out.println("消费者消费了一个" + obj + "， 还剩" + queue.size());
            }
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
            synchronized (queue) {
                // 写一个循环判断是满了， 如果满了就让该线程等待
                while (queue.size() == maxSize) {
                    queue.notify();
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.add("包子");
                System.out.println("生产者生产了一个包子，还剩" + queue.size());
            }
        }
    }
}