package 韩顺平Java.类加载顺序;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class B <T>{
    private T t;

    public B(T t) {
        this.t = t;
    }

    public static void main(String[] args) {
        B<A> b = new B<>(new A());
        SoftReference<String> softReference = new SoftReference<String>("abc");
        WeakReference<String> weakReference = new WeakReference<>("abc");
    }
}
