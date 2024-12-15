package singletonlearn;

import java.io.Serializable;

public class Singleton1 implements Serializable {
    private static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
        if (INSTANCE != null){
            throw new RuntimeException("单例对象不能重复创建");
        }
    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }

    public Object readResolve(){
        return INSTANCE;
    }
}
