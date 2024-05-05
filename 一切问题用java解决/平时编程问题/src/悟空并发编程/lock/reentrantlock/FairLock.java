package 悟空并发编程.lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 演示公平和不公平两种情况
 *
 * */
public class FairLock {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Job job = new Job(printQueue);
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(job, "线程" + (i + 1));
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Job implements Runnable {
        private PrintQueue printQueue;

        public Job(PrintQueue printQueue) {
            this.printQueue = printQueue;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "开始打印");
            printQueue.printJob(new Object());
            System.out.println(Thread.currentThread().getName() + "打印完毕");
        }
    }

    static class PrintQueue {
        private Lock queueLock = new ReentrantLock(false);


        private void printOnce(String to) {
            queueLock.lock();
            try {
                // 临界区代码
                Long duration = (long) (Math.random() * 10000);
                System.out.println(Thread.currentThread().getName() + "正在给" + to + "打印，需要" + duration + "毫秒...");
                try {
                    Thread.sleep(duration.intValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                queueLock.unlock();
            }
        }

        public void printJob(Object document) {
            printOnce("Student");
            printOnce("Teacher");
        }
    }
}
/*
*
*  线程1开始打印
线程5开始打印
线程3开始打印
线程4开始打印
线程2开始打印
线程8开始打印
线程7开始打印
线程6开始打印
线程10开始打印
线程9开始打印
*
* */