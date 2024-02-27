package 韩顺平Java.异常.异常处理细节;

import java.io.IOException;

public class Exception01 {
    public int read(int i) throws Exception {
        if(i == 0){
            throw new Exception();
        }
        try {
            int i1 = 1 / i;
            return i1;
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            System.out.println("finally被执行了");
        }
    }

    public static void main(String[] args) {
        Exception01 exception01 = new Exception01();
//        exception01.read(0);
    }
}
