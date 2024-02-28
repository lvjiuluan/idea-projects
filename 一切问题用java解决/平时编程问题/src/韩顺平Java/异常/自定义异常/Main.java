package 韩顺平Java.异常.自定义异常;

public class Main {
    public static void m(){
        throw new RuntimeException();
    }
    public static void main(String[] args) {
        MyRuntimeException myE = new MyRuntimeException("aaa");
        myE.printStackTrace();
        String message = myE.getMessage();
        System.out.println(message);
    }
}
