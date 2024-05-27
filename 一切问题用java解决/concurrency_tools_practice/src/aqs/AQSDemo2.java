package aqs;

import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo2 {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock(true);
    }
}
