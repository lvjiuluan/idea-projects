package 韩顺平Java.类加载顺序;

public class A {
    String s1 = "abc";
    String s2 = new String("abc");
    Integer i1 = 12;
    Integer i2 = new Integer(12);

    public static void dfs(int i){
        System.out.println(i++);
        dfs(i);
    }

    public static void main(String[] args) {
        dfs(0);
        // 10K can not create a Java Virtual Machine
        // 108K 973
        // 1M 11407
        // 10M 98582

    }
}
