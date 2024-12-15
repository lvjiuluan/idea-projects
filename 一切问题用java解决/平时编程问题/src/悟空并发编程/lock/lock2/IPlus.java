package 悟空并发编程.lock.lock2;

import java.util.concurrent.atomic.AtomicInteger;

public class IPlus {
    public static void main(String[] args) throws InterruptedException {
        ITask iTask = new ITask();
        ISynTask iSynTask = new ISynTask();
        IAtomTask iAtomTask = new IAtomTask();

        for (int i = 0; i < 1000; i++) {
            new Thread(iTask).start();
        }
        for (int i = 0; i < 1000; i++) {
            new Thread(iSynTask).start();
        }
        for (int i = 0; i < 1000; i++) {
            new Thread(iAtomTask).start();
        }

        Thread.sleep(5000);
        System.out.println("iTask = " + iTask.count);
        System.out.println("iSynTask = " + iSynTask.count);
        System.out.println("iAtomTask = " + iAtomTask.count);
    }
}

class ITask implements Runnable{
    int count = 0;
    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            count++;
        }
    }
}

class ISynTask implements Runnable{
    int count = 0;

    @Override
    synchronized public void run() {
        for (int j = 0; j < 1000; j++) {
            count++;
        }
    }
}

class IAtomTask implements Runnable{

    AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        for (int j = 0; j < 1000; j++) {
            count.incrementAndGet();
        }
    }
}
