package pre.Object_;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode subNode = p.subNodes.get(c);
            if (subNode == null) {
                subNode = new TrieNode();
                p.subNodes.put(c, subNode);
            }
            p = subNode;
        }
        p.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode subNode = p.subNodes.get(c);
            if (subNode == null) {
                return false;
            }
            p = subNode;
        }
        return p.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            TrieNode subNode = p.subNodes.get(c);
            if (subNode == null) {
                return false;
            }
            p = subNode;
        }
        return true;
    }
}

class TrieNode {
    public boolean isEnd = false;
    public Map<Character, TrieNode> subNodes = new HashMap<>();
}
