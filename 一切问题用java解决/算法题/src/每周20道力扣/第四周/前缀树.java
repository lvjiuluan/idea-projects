package 每周20道力扣.第四周;

public class 前缀树 {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));  // 返回 True
        System.out.println(trie.search("app"));     // 返回 False
        System.out.println(trie.startsWith("app")); // 返回 True
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 True
    }
}

class TrieNode {
    boolean isEnd;
    TrieNode[] characters;

    public TrieNode() {
        this.isEnd = false;
        this.characters = new TrieNode[26]; // 代表26个英文字符
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (p.characters[c - 'a'] == null) {
                p.characters[c - 'a'] = new TrieNode();
            }
            p = p.characters[c - 'a'];
        }
        p.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (p.characters[c - 'a'] == null) {
                return false;
            }
            p = p.characters[c - 'a'];
        }
        return p.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (p.characters[c - 'a'] == null) {
                return false;
            }
            p = p.characters[c - 'a'];
        }
        return true;
    }
}