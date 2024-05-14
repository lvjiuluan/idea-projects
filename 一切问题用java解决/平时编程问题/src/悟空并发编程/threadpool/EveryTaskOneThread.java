package 悟空并发编程.threadpool;

import java.util.concurrent.Executors;

public class EveryTaskOneThread {
    public static void main(String[] args) {
        Executors.defaultThreadFactory();
    }
}
