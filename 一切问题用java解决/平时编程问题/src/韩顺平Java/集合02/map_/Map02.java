package 韩顺平Java.集合02.map_;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Map02 {
    static int i;

    public static void main(String[] args) {
        Map map = new HashMap();
        Map02 map02 = new Map02();
        A a = map02.get();
        map.put(a, "");
        System.out.println();

       /* Set entrySet = map.entrySet();
        Set keySet = map.keySet();
        Set mySet = new HashSet();

        System.out.println(entrySet);*/

    }

    public A get() {
        return new A();
    }

    class A {
        public A() {
            i++;
            System.out.println(Map02.this);
        }
    }
}


