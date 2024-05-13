package 韩顺平Java.HashMap扩容;

import java.util.HashMap;

public class HashMap02 {
    public static void main(String[] args) {
        HashMap<Object, Object> hashMap = new HashMap<>(3);
        hashMap.put(1,1);
        hashMap.put(2,1);
        hashMap.put(3,1);
        hashMap.put(4,1);
        System.out.println(hashMap);
    }
}
