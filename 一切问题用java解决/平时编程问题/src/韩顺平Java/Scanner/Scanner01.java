package 韩顺平Java.Scanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Scanner01 {
    public static void main(String[] args) throws IOException, InterruptedException {
        InputStream in = System.in;
//        InputStreamReader inputStreamReader = new InputStreamReader(in);
        Scanner scanner = new Scanner(in);
        scanner.nextInt();
        scanner.nextInt();
        scanner.close();
    }
}
