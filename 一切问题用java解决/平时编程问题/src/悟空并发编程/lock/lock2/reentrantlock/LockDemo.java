package 悟空并发编程.lock.lock2.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static final Outputer outputer = new Outputer();

    public static void main(String[] args) {
        String[] names = {"悟空","猪八戒","沙和尚","玉皇大帝"};
        for (int i = 0; i < names.length; i++) {
            int finalI = i;
            new Thread(()->{
                while (true){
                    try {
                        Thread.sleep(5);
                        outputer.output(names[finalI]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static class Outputer{
        Lock lock = new ReentrantLock();

        public void output(String name){
            int len = name.length();
//            lock.lock();
            try {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println("");
            }finally {
//                lock.unlock();
            }
        }
    }


}
