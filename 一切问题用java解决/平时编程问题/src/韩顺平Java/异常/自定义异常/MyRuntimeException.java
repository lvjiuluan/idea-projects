package 韩顺平Java.异常.自定义异常;

public class MyRuntimeException extends RuntimeException{
    public MyRuntimeException(String message) {
        super(message);
    }
}
