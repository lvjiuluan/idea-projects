package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示公平和非公平两种情况
 */
public class FairLock {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0){
                threads[i] = new Thread(new Job(printQueue),"tryLock-"+i);
            }else {
                threads[i] = new Thread(new Job(printQueue),"lock-"+i);
            }
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Job implements Runnable{
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"开始打印");
        printQueue.printJob(new Object());
        System.out.println(Thread.currentThread().getName()+"结束打印");
    }
}

class PrintQueue{
    private Lock queueLock = new ReentrantLock(false);

    public void printJob(Object document){
        String s = Thread.currentThread().getName().split("-")[0];
        if("tryLock".equals(s)){
            if (queueLock.tryLock()){
                try {
                    Long duration = (long)(Math.random() * 10000) + 1000;
                    System.out.println(Thread.currentThread().getName() + "正在打印...需要"+duration/1000+"秒");
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    queueLock.unlock();
                }
            }else {
                System.out.println(Thread.currentThread().getName()+"获取锁失败");
            }

            if (queueLock.tryLock()){
                try {
                    Long duration = (long)(Math.random() * 10000) + 1000;
                    System.out.println(Thread.currentThread().getName() + "正在打印...需要"+duration/1000+"秒");
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    queueLock.unlock();
                }
            }else {
                System.out.println(Thread.currentThread().getName()+"获取锁失败");
            }
        }else {
            queueLock.lock();
            try {
                Long duration = (long)(Math.random() * 10000) + 1000;
                System.out.println(Thread.currentThread().getName() + "正在打印...需要"+duration/1000+"秒");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                queueLock.unlock();
            }

            queueLock.lock();
            try {
                Long duration = (long)(Math.random() * 10000) + 1000;
                System.out.println(Thread.currentThread().getName() + "正在打印...需要"+ (duration/1000) +"秒");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                queueLock.unlock();
            }
        }

    }
}
