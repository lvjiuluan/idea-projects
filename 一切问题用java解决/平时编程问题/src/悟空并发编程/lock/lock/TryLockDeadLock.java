package 悟空并发编程.lock.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 使用tryLock来避免死锁
 * */
public class TryLockDeadLock implements Runnable {

    int flag = 1;

    public TryLockDeadLock(int flag) {
        this.flag = flag;
    }

    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new TryLockDeadLock(1), "线程1");
        threads[1] = new Thread(new TryLockDeadLock(2), "线程2");
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                                    System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                                    break;
                                } finally {
                                    lock2.unlock();
                                    System.out.println(Thread.currentThread().getName() + "释放了锁2");
                                    Thread.sleep(new Random().nextInt(1000));
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取锁2失败，已重试");
                            }
                        } finally {
                            lock1.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放了锁1");
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (flag == 2) {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + "获取到了锁2");
                            Thread.sleep(new Random().nextInt(1000));
                            if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + "获取到了锁1");
                                    System.out.println(Thread.currentThread().getName() + "成功获取到了两把锁");
                                    break;
                                } finally {
                                    lock1.unlock();
                                    System.out.println(Thread.currentThread().getName() + "释放了锁1");
                                    Thread.sleep(new Random().nextInt(1000));
                                }
                            } else {
                                System.out.println(Thread.currentThread().getName() + "获取锁1失败，已重试");
                            }
                        } finally {
                            lock2.unlock();
                            System.out.println(Thread.currentThread().getName() + "释放了锁2");
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println(Thread.currentThread().getName() + "获取锁2失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
