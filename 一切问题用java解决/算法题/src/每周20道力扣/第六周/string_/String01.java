package 每周20道力扣.第六周.string_;

public class String01 {

    // 1 String对象用于保存字符串，也就是一组字符序列
    // 2 "jack" 是一个字符串字面量
    // 3 String有很多构造器
    // 4 String实现了3个接口
    // 5 String是final的，表示不能被继承
    String name = "jack";

    public static void main(String[] args) {
        final int[] v = {1, 2, 3};
        int[] b = {4, 5, 6};
        v[0] = 1;
        String hsp = "hsp";
        System.out.println(hsp.hashCode());
        System.out.println("hsp".hashCode());
        String hsp2 = new String("hsp");
        System.out.println(hsp2.hashCode());
        System.out.println(hsp2==hsp);
        String intern = hsp.intern();
    }
}
