package 每周20道力扣.敏感词过滤;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Char2IntMapUtils {
    public static Map<String, Integer> generateChar2IntMap() {
        Map<String, Integer> char2IntMap = new HashMap<>();
        URL url = Char2IntMapUtils.class.getResource("/sensitive-words.txt");
        BufferedReader bufferedReader = null;
        try {
            URI uri = url.toURI();
            File file = new File(uri);
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    char2IntMap.put(String.valueOf(c), char2IntMap.size());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return char2IntMap;
    }
}
