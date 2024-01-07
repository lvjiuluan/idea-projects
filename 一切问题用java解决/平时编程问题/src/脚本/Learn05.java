package 脚本;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
*
 * */
public class Learn05 {
    public static void main(String[] args) throws MalformedURLException {
        String fileName = "4690f8fa937e43c990d5e209f45059a3moon.jpg";
        Path path = Paths.get("D:\\Idea Projects\\community\\target\\classes\\static\\upload\\4690f8fa937e43c990d5e209f45059a3moon.jpg");
//        URL url = path.toUri().toURL();
//        System.out.println(url);
        URI uri = path.toUri();
        System.out.println(uri);
        URL url = uri.toURL();
        System.out.println(url);
        int uploadIndex = 0;
        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).toString().equals("upload")) {
                uploadIndex = i;
                break;
            }
        }
        Path subpath = path.subpath(uploadIndex, path.getNameCount());
//        Path parent = path.;
        System.out.println(subpath);

    }
}
