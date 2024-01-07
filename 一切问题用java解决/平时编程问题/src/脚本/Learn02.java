package 脚本;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Learn02 {
    public static void main(String[] args) throws UnsupportedEncodingException, URISyntaxException {
        URL resource = Learn02.class.getResource("/");
        Path path = Paths.get(resource.toURI());
        System.out.println(path);
        File file = new File(path.toAbsolutePath().toString());
        System.out.println(file.exists());
        System.out.println(file.isDirectory());
    }
}
