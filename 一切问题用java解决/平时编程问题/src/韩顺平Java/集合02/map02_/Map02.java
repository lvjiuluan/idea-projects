package 韩顺平Java.集合02.map02_;

import java.util.HashMap;
import java.util.Map;

public class Map02 {
    public static void main(String[] args) {
        Map map = new HashMap();
        A[] a = new A[20];
        for (int i = 0; i < 20; i++) {
            a[i] = new A();
        }
        for (int i = 0; i < 11; i++) {
            map.put(a[i], i);
        }
        for (int i = 0; i < 6; i++) {
            map.remove(a[i]);
        }
        map.remove(a[6]);
        System.out.println(map);
    }
}
