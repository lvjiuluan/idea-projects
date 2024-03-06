package 韩顺平Java.集合02;

import java.util.ArrayList;

public class 泛型数组 {
    public static void main(String[] args) {
        A[] arr = new A<?>[10];
        arr[0] = new A(1);
        arr[1] = new A(new ArrayList());
    }
}

class A<K> {
    K i;

    public A(K i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "A{" +
                "i=" + i +
                '}';
    }
}
