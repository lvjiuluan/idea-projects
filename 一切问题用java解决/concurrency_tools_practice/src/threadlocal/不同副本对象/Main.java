package threadlocal.不同副本对象;

public class Main {
    public static void main(String[] args) {

        // 创建并启动第一个线程
        new Thread(() -> {
            UserContextHolder.holder.set(new User("John"));
            System.out.println(Thread.currentThread().getName() + " User: " + UserContextHolder.holder.get());
            System.out.println(Thread.currentThread().getName() + " User Identity: " + System.identityHashCode(UserContextHolder.holder.get()));
        }, "Thread-1").start();

        // 创建并启动第二个线程
        new Thread(() -> {
            UserContextHolder.holder.set(new User("John"));
            System.out.println(Thread.currentThread().getName() + " User: " + UserContextHolder.holder.get());
            System.out.println(Thread.currentThread().getName() + " User Identity: " + System.identityHashCode(UserContextHolder.holder.get()));
        }, "Thread-2").start();
    }
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }


}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}
