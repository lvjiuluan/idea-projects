package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
 * TODO 1 将*用一个常量替换，以后方便修改
 *      2 用@PostContstuct来代替直接写构造函数
 *      3 用classLoader获取资源，不用加'/'就直接中本类的classpath路径查找
 *        而用class对象获取资源，不加'/'就回去SensitiveFilter.class所在包的文件夹查找
 *        而造成查找失败
 *      4 非递归写法
 *      5 对传进来的文本判空
 *      6 #开#票 也是敏感词，这种要查出来
 *
 *
 * */
@Component
public class SensitiveFilter {
    // 定义树形结构 Trie
    // 初始化数据 根据sensitive-words.txt
    private Trie trie;

    @PostConstruct
    public void init() {
        trie = new Trie();
        /*
         * 必须加 '/'
         * 如果不加，不回去本类的classpath路径下查找，而是会去SensitiveFilter.class所在包文件夹查找
         * */
        InputStream inputStream = this.getClass().getResourceAsStream("/sensitive-words.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((inputStream)));
        while (true) {
            try {
                String word = bufferedReader.readLine();
                if (word == null) break;
                trie.insert(word);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 判断是否为符号
    private boolean isSymbol(char c) {
        return (!CharUtils.isAsciiAlphanumeric(c)) && (c < 0x2E80 || c > 0x9FFF);
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
                    if (isSymbol(words.charAt(k))) sb.append(words.charAt(k));
                    else sb.append("*");
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

class TrieNode {
    Map<String, TrieNode> children = new HashMap<>();
    boolean isEnd;
}

class Trie {
    TrieNode ROOT;

    Trie() {
        ROOT = new TrieNode();
    }

    private void insert(TrieNode root, String word, int i) {
        if (i >= word.length()) return;
        String s = word.substring(i, i + 1);
        if (root.children.get(s) == null) {
            root.children.put(s, new TrieNode());
        }
        TrieNode childNode = root.children.get(s);
        if (i == word.length() - 1) childNode.isEnd = true;
        insert(childNode, word, i + 1);
    }

    public void insert(String word) {
        insert(ROOT, word, 0);
    }

    private boolean search(TrieNode root, String word, int i) {
        if (i >= word.length()) return false;
        String s = word.substring(i, i + 1);
        if (root.children.get(s) == null) return false;
        TrieNode childNode = root.children.get(s);
        if (i == word.length() - 1 && childNode.isEnd) return true;
        return search(childNode, word, i + 1);
    }

    public boolean search(String word) {
        return search(ROOT, word, 0);
    }

    private boolean startsWith(TrieNode root, String prefix, int i) {
        if (i >= prefix.length()) return false;
        String s = prefix.substring(i, i + 1);
        if (root.children.get(s) == null) return false;
        TrieNode childNode = root.children.get(s);
        if (i == prefix.length() - 1) return true;
        return startsWith(childNode, prefix, i + 1);
    }

    public boolean startsWith(String prefix) {
        return startsWith(ROOT, prefix, 0);
    }

}

