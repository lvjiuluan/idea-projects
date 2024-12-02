package 悟空并发编程.threadpool02;

public class Father {

    static ThreadLocal threadLocal1 = new ThreadLocal<>();
    static ThreadLocal threadLocal2 = new ThreadLocal<>();
    static ThreadLocal threadLocal3 = new ThreadLocal<>();

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            new Thread(
                    () -> {
                        threadLocal1.set(123);
                        System.out.println(threadLocal2.get());

                        threadLocal2.set("abc");
                        System.out.println(threadLocal2.get());

                        threadLocal3.set(new User("Jack",18));
                        System.out.println(threadLocal3.get());
                    }
            ).start();
        }
    }
}

