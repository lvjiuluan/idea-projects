package 悟空并发编程.threadpool02;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PauseableThreadPool extends ThreadPoolExecutor {
    // 标识线程池是否处于暂停状态
    private boolean isPaused;
    // 可重入锁，用于控制线程池的暂停和恢复操作
    private final ReentrantLock lock = new ReentrantLock();
    // 条件变量，用于在暂停时阻塞线程，恢复时唤醒线程
    private Condition condition = lock.newCondition();

    // 构造方法1：初始化线程池，指定核心线程数、最大线程数、线程存活时间、时间单位和任务队列
    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    // 构造方法2：额外指定线程工厂
    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    // 构造方法3：额外指定拒绝策略
    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    // 构造方法4：同时指定线程工厂和拒绝策略
    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    // 暂停线程池的执行
    private void pause(){
        lock.lock(); // 获取锁
        try {
            isPaused = true; // 设置线程池为暂停状态
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    // 恢复线程池的执行
    private void resume(){
        lock.lock(); // 获取锁
        try {
            isPaused = false; // 设置线程池为非暂停状态
            condition.signalAll(); // 唤醒所有等待的线程
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    // 在每个任务执行前调用，用于检查线程池是否处于暂停状态
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r); // 调用父类的beforeExecute方法
        lock.lock(); // 获取锁
        try {
            // 如果线程池处于暂停状态，当前线程等待
            while (isPaused){
                condition.await(); // 线程等待，直到被唤醒
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // 捕获中断异常
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    // 主方法，测试线程池的暂停和恢复功能
    public static void main(String[] args) throws InterruptedException {
        // 创建一个PauseableThreadPool实例，核心线程数和最大线程数为10，线程存活时间为10秒
        PauseableThreadPool pauseableThreadPool = new PauseableThreadPool(10,
                10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        // 提交1000个任务到线程池
        for (int i = 0; i < 1000; i++) {
            pauseableThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 每个任务打印当前线程名称并休眠10毫秒
                    System.out.println(Thread.currentThread().getName() + "我被执行");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 主线程休眠1.5秒后暂停线程池
        Thread.sleep(1500);
        pauseableThreadPool.pause();
        System.out.println("线程池被暂停了");

        // 主线程再休眠1.5秒后恢复线程池
        Thread.sleep(1500);
        pauseableThreadPool.resume();
    }
}

