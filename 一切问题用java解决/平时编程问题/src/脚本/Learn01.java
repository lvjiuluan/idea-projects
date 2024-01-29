package 脚本;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Learn01 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        /*Class<Learn01> learn01Class = Learn01.class;
        Learn01 learn01 = new Learn01();
        Class<Learn01> learn01Class1 = (Class<Learn01>) learn01.getClass();
        System.out.println(learn01Class1 == learn01Class1);*/
        Class<Learn01> learn01Class = Learn01.class;
        /*
         * 这里如果name以斜杠'/'开头，则从本类classpath开始的路径为绝对路径起点开始查找
         * / 表示 本类的classpath路径，class文件所在顶层包的文件夹路径
         * 如果name不以斜杆开头，则会以class文件所在包的文件夹为参照系，用相对路径查找
         * */
        URL url = learn01Class.getResource("/temp/references.bib");
        String pathString = url.getPath();
        pathString = URLDecoder.decode(pathString, StandardCharsets.UTF_8.toString());
        System.out.println(pathString);
        File file = new File(pathString);
        System.out.println(file.exists());

        System.out.println(file.getPath());

        Path path = Paths.get(file.getPath());
        System.out.println(path.getFileName());
    }
}
