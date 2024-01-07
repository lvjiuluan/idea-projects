package 脚本;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Learn04 {
    public static void copyFileToUpload(File file) throws IOException, URISyntaxException {
        URI resource = Learn04.class.getResource("/").toURI();
        String directoryPathStr = Paths.get(resource).toString();
        Path directoryPath = Paths.get(directoryPathStr, "upload");
        System.out.println(directoryPath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        Files.copy(fileInputStream, directoryPath.resolve(file.getName()));
        fileInputStream.close();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = new File("D:\\帅地_后端训练营\\2023-12-10~2023-1-10\\项目与框架\\项目2 一周\\cat.jpg");
        System.out.println(file.exists());
        System.out.println(file.getName());
        copyFileToUpload(file);
    }
}

/*
*
*   @Override
  public void save(MultipartFile file) {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }
*
* */