package 韩顺平Java.JVM引用;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class A {
    public static void main(String[] args) {
        // 强引用，只要b引用着对象，垃圾回收的时候绝对不会回收它。
        B b1 = new B();

        // 软引用，正常情况下，垃圾回收不会回收软引用指向的对象
        // 但是如果垃圾回收之后，内存还是不够，就会回收软引用指向的对象
        SoftReference<B> b2 = new SoftReference<B>(new B());

        // 弱引用就和没引用的情况一样，一旦发生垃圾回收，就会清理
        // 掉没有引用指向的对象和弱引用指向的对象。
        WeakReference<B> b3 = new WeakReference<>(new B());

    }
}
