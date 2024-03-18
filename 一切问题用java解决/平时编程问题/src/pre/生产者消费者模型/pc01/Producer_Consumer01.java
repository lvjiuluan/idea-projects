package pre.生产者消费者模型.pc01;

import java.util.LinkedList;
import java.util.Queue;

/* 不使用 synchronized
 结果：造成线程忙等
 */


public class Producer_Consumer01 {
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
            if (!queue.isEmpty()) {
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
            if (queue.size() < maxSize) {
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
