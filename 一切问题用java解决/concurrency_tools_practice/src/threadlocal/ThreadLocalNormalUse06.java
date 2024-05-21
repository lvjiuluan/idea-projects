package threadlocal;

import java.text.SimpleDateFormat;

/*
 *
 * 描述：演示ThreadLocal用法2，避免传递参数的麻烦
 * */
public class ThreadLocalNormalUse06 {
    public static void main(String[] args) {
        User user = new User("张超");
        UserContextHolder.holder.set(user);
        System.out.println(UserContextHolder.holder.get());
        Thread thread = new Thread(() -> {
            UserContextHolder.holder.set(user);
            System.out.println(UserContextHolder.holder.get());
        });
        thread.start();
    }
}

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}

class Service1 {
    public void process() {
        User user = new User("超哥");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.holder.get();
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println("Service2拿到用户名：" + user.name);
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
    }
}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}