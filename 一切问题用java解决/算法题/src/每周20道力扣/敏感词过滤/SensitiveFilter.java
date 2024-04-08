package 每周20道力扣.敏感词过滤;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class SensitiveFilter {
    public Trie trie;

    public void init() {
        trie = new Trie();
        URL url = Char2IntMapUtils.class.getResource("/sensitive-words.txt");
        BufferedReader bufferedReader = null;
        try {
            URI uri = url.toURI();
            File file = new File(uri);
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                trie.insert(line);
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
    }

    // 判断是否为符号
    public boolean isSymbol(char c) {
        if (Character.isDigit(c) || Character.isLetter(c) || Character.isIdeographic(c)) {
            return false;
        }
        return true;
    }

    private String removeSymbol(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (isSymbol(s.charAt(i))) {
                continue;
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }


    // 替换一段话的敏感词为**
    public String replaceSensitiveWords(String words) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < words.length()) {
            int j = i + 1;
            while (j <= words.length() && trie.startsWith(removeSymbol(words.substring(i, j)))) {
                j++;
            }
            j--;
            if (trie.search(removeSymbol(words.substring(i, j)))) {
                for (int k = i; k < j; k++) {
                    if (isSymbol(words.charAt(k))) {
                        sb.append(words.charAt(k));
                    } else {
                        sb.append("*");
                    }
                }
                i = j;
            } else {
                sb.append(words.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }
}
