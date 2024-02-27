package 韩顺平Java.异常.异常处理机制;

public class A {
    public int m(int i){
        return ++i;
    }
    public int m2(int i){
        return i++;
    }
    public static void main(String[] args) {
        int i = 0;
        i++;
        System.out.println(i);
    }
}

