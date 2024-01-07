package 脚本;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Learn03 {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\Idea Projects\\community\\upload", "temp","cat.jpg");
        System.out.println(path);
    }
}
