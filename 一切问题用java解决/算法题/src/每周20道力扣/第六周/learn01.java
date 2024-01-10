package 每周20道力扣.第六周;

import java.io.IOException;

public class learn01 {
    public static void hello(int n) throws IOException {
        if (n < 0) {
            throw new IOException();
        }
    }

    public static void main(String[] args) throws IOException {
        hello(0);
    }
}
