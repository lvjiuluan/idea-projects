package 每周20道力扣.敏感词过滤;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (p.char2TNodeMap.get(String.valueOf(c)) == null) {
                p.char2TNodeMap.put(String.valueOf(c), new TrieNode());
            }
            p = p.char2TNodeMap.get(String.valueOf(c));
        }
        p.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (p.char2TNodeMap.get(String.valueOf(c)) == null) {
                return false;
            }
            p = p.char2TNodeMap.get(String.valueOf(c));
        }
        return p.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (p.char2TNodeMap.get(String.valueOf(c)) == null) {
                return false;
            }
            p = p.char2TNodeMap.get(String.valueOf(c));
        }
        return true;
    }
}

class TrieNode {
    boolean isEnd;
    Map<String, TrieNode> char2TNodeMap;

    public TrieNode() {
        this.isEnd = false;
        this.char2TNodeMap = new HashMap<>();
    }
}