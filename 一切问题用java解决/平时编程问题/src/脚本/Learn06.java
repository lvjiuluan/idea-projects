package 脚本;

import java.io.*;

public class Learn06 {
    public static void main(String[] args) throws IOException {
        Reader reader = new BufferedReader(new FileReader("titles.txt"));
        int c = reader.read();
        System.out.println((char) c);
        reader.close();
    }
}
