package 韩顺平Java.集合02.list_.ArrayList_;

import java.util.ArrayList;
import java.util.List;

public class ArrayList01 {
    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 0; i < 12; i++) {
            list.add(i);
        }
        list.remove(3);
        System.out.println(list);
    }
}
/*
 * @533
 * @535
 *
 * */