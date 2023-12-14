package head_first_java.chapter02;

public class TestIntToByte {


    public void setB(byte b) {
        System.out.println(b);
    }

    public static void main(String[] args) {
        int x = 24;
        byte b = (byte) x;
        System.out.println(b);
    }
}
