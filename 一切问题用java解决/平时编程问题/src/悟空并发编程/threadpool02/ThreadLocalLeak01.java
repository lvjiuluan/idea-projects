package 悟空并发编程.threadpool02;

public class ThreadLocalLeak01 {
    public static void main(String[] args) {
        ThreadLocal<User> local = new ThreadLocal<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                local.set(new User("jack",18));
            }
        });
    }
}
class User{
    String name;
    int age;

    public User(String age, int name) {
        this.name = age;
        this.age = name;
    }
}