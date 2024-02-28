package 韩顺平Java.异常.自定义异常;

import java.io.FileNotFoundException;

public class Main {
    public static void m(){
        throw new StackOverflowError();
    }
    public static void main(String[] args) {
    }
}
