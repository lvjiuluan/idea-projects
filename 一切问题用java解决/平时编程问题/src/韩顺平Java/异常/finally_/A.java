package 韩顺平Java.异常.finally_;

public class A {
    public static void m1(){
        try{
            System.out.println("进入方法m1");
            int i = 1 / 0;
        }
        finally {
            System.out.println("执行m1的finally");
        }
    }

    public static void main(String[] args) {
        m1();
    }
}
