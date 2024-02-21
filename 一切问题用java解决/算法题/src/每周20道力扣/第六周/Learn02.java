package 每周20道力扣.第六周;

public class Learn02 {
    public native void myMethod(); //用c实现打印一句hello word

    public static void main(String[] args) {
        Learn02 learn02 = new Learn02();
        learn02.myMethod();
    }
}
