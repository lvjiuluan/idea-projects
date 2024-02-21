package thread.作业;

import java.util.Scanner;

public class Homwork01 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        T2 t2 = new T2();
        new Thread(t1).start();
        new Thread(t2).start();

    }
}

class T1 implements Runnable {

    public static void setLoop(boolean loop) {
        T1.loop = loop;
    }

    private static boolean loop = true;

    @Override
    public void run() {
        while (loop) {
            System.out.println((int) (Math.random() * 100));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class T2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.print("请输入: ");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            if ("Q".equals(s)) {
                T1.setLoop(false);
                break;
            }
        }
    }
}