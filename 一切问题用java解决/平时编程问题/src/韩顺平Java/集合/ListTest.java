package 韩顺平Java.集合;


import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ListTest {
    public static void main(String[] args) {
        Set<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(2);
    }
}


