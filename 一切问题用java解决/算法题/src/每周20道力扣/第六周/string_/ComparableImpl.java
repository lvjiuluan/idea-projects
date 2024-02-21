package 每周20道力扣.第六周.string_;

import java.util.*;

public class ComparableImpl implements Comparable<ComparableImpl> {

    int i;

    @Override
    public int compareTo(ComparableImpl another) {
        return this.i - another.i;
    }

    ComparableImpl(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }

    public static void main(String[] args) {
//        List<ComparableImpl> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(new ComparableImpl((int) (Math.random() * 20)));
//        }
//        System.out.println(list);
//        Collections.sort(list);
//        // 本质上调用list的sort
////        list.sort(null);
//        System.out.println(list);
        SortedSet<ComparableImpl> sortedSet = new TreeSet<>();
        ComparableImpl a = new ComparableImpl(1);
        ComparableImpl b = new ComparableImpl(1);
        sortedSet.add(a);
        sortedSet.add(b);
        System.out.println(a.equals(b));
        System.out.println(sortedSet);
    }
}
