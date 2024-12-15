package 悟空并发编程.lock.lock2;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDead implements Runnable{
    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDead tryLockDead1 = new TryLockDead();
        TryLockDead tryLockDead2 = new TryLockDead();
        tryLockDead1.flag = 1;
        tryLockDead2.flag = 2;
        new Thread(tryLockDead1).start();
        new Thread(tryLockDead2).start();
    }

    @Override
    public void run() {
        while (true){
            if (flag == 1){
                try {
                    if(lock1.tryLock(800, TimeUnit.MILLISECONDS)){
                        try {
                            System.out.println("线程1获取到了锁1");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock2.tryLock(800,TimeUnit.MILLISECONDS)){
                                try {
                                    System.out.println("线程1获取到了锁2");
                                    System.out.println("线程1成功获取到了两把锁");
                                    break;
                                }finally {
                                    lock2.unlock();
                                }
                            }else {
                                System.out.println("线程1获取锁2失败，已重试");
                            }
                        }finally {
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }

                    }else {
                        System.out.println("线程1获取锁1失败，已重试");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if (flag == 2){
                try {
                    if(lock2.tryLock(800, TimeUnit.MILLISECONDS)){
                        try {
                            System.out.println("线程2获取到了锁2");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock1.tryLock(800,TimeUnit.MILLISECONDS)){
                                try {
                                    System.out.println("线程2获取到了锁1");
                                    System.out.println("线程2成功获取到了两把锁");
                                    break;
                                }finally {
                                    lock1.unlock();
                                }
                            }else {
                                System.out.println("线程2获取锁1失败，已重试");
                            }
                        }finally {
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }

                    }else {
                        System.out.println("线程2获取锁2失败，已重试");
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
