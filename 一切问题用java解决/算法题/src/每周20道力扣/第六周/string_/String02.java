package 每周20道力扣.第六周.string_;

public class String02 {
    public static void main(String[] args) {
        String a = "hello";
        String b = "abc";



        String c = a + b;
        // 1 创建StringBuilder --> public StringBuilder()
        // 2 "hello" -(append)-> 到StringBuilder对象
        // 3 "abc"  -(append)->  到StringBuilder对象
        // 4  调用StringBuilder对象的toString方法
        // 5 该toString 方法会调用new String(value, 0, count);
        // new String("helloabc", 0, count);

    }
}
