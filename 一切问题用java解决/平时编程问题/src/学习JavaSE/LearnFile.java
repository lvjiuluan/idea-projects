package 学习JavaSE;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class LearnFile {
    public static void main(String[] args) throws IOException {
        File file = new File("myFile.txt");
        /*
         * 创建文件的操作是原子性的，要么成功，要么失败，没有中间态
         * 不可被切分
         * */
//        if (file.createNewFile()) {
//            System.out.println("SUCCESS");
//        }
//        String absolutePath = file.getAbsolutePath();
//        System.out.println(absolutePath);
//        boolean exists = file.exists();
//        System.out.println(exists);
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(System.getProperty("java.class.path"));
//        System.out.println(LearnFile.class.getResource("/"));
        URL resource = LearnFile.class.getResource("LearnFile.class");
        String file1 = resource.getFile();
        String decodedResource = URLDecoder.decode(resource.toString(), "utf-8");
        System.out.println(decodedResource);

    }
}
