package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.Map;

public class Map08 {
    public static void main(String[] args) {
        Map map = new HashMap();
        B[] arr = new B[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = new B();
        }
        for (int i = 0; i < 12; i++) {
            map.put(arr[i], i);
        }
        for (int i = 0; i < 8; i++) {
            map.remove(arr[i]);
        }
        map.remove(arr[8]);
        System.out.println(map);
    }
}

class B {
    @Override
    public int hashCode() {
        return 1;
    }
}