package thread.生产者.learn01;

import java.util.ArrayDeque;
import java.util.Queue;
/*
*
* 1、使用synchronized：解决线程不安全问题。
* 2、使用wait notify 解决忙等问题
*
*
* */

public class Learn01 {
    public static void main(String[] args) {
        Queue<String> q = new ArrayDeque<>();
        int cacheSize = 10;
        Consumer consumer = new Consumer(q, cacheSize);
        Producer producer = new Producer(q, cacheSize);
        new Thread(consumer).start();
        new Thread(producer).start();
    }
}

class Consumer implements Runnable {

    int count = 0;

    private Queue<String> q;

    private int cacheSize;

    @Override
    public void run() {
        while (true) {
            synchronized (q){
                while (q.isEmpty()){
                    System.out.println("当前队列为空");
                    q.notify();
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println((++count) + "消费者消费了一个" + q.poll() + ", 还剩" + q.size());
                q.notify();
            }
        }
    }

    public Consumer(Queue<String> q, int cacheSize) {
        this.q = q;
        this.cacheSize = cacheSize;
    }
}


class Producer implements Runnable {

    int count = 0;

    private Queue<String> q;

    private int cacheSize;

    @Override
    public void run() {
        while (true) {
            synchronized (q){
                while (q.size() == cacheSize){
                    System.out.println("队列已满");
                    q.notify();
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                q.add("包子");
                System.out.println((++count)+"生成者生产了一个包子, 还剩" + q.size());
                q.notify();
            }
        }
    }

    public Producer(Queue<String> q, int cacheSize) {
        this.q = q;
        this.cacheSize = cacheSize;
    }
}