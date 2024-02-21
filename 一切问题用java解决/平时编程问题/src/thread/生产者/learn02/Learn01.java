package thread.生产者.learn02;

import java.util.ArrayDeque;
import java.util.Queue;

public class Learn01 {
    public static void main(String[] args) {
//        Queue<String> q = new ArrayDeque<>();
//        int cacheSize = 10;
//        Consumer consumer = new Consumer(q, cacheSize);
//        Producer producer = new Producer(q, cacheSize);
//        new Thread(consumer).start();
//        new Thread(producer).start();
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
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!q.isEmpty()) {
                    System.out.println((++count) + "消费者消费了一个" + q.poll() + ", 还剩" + q.size());
                }
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
            synchronized (q) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (q.size() < cacheSize) {
                    q.add("包子");
                    System.out.println((++count) + "生成者生产了一个包子, 还剩" + q.size());
                }
            }
        }
    }

    public Producer(Queue<String> q, int cacheSize) {
        this.q = q;
        this.cacheSize = cacheSize;
    }
}