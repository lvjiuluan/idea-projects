package 悟空并发编程.threadpool02;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {
    public static void main(String[] args) {
        String str = new String("abc");
        WeakReference<String> stringWeakReference = new WeakReference<>(str);
    }
}
