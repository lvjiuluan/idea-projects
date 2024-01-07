package 脚本;

public class Trie {
    TrieNode ROOT;

    Trie() {
        ROOT = new TrieNode();
    }

    private void insert(TrieNode root, String word, int i) {
        if (i >= word.length()) return;
        int c = word.charAt(i) - 'a';
        if (root.children[c] == null) {
            root.children[c] = new TrieNode();
        }
        if (i == word.length() - 1) {
            root.children[c].isEnd = true;
        }
        insert(root.children[c], word, i + 1);
    }

    public void insert(String word) {
        insert(ROOT, word, 0);
    }

    private boolean search(TrieNode root, String word, int i) {
        if (i >= word.length()) return false;
        int c = word.charAt(i) - 'a';
        if (root.children[c] == null) return false;
        if (root.children[c].isEnd && i == word.length() - 1) return true;
        return search(root.children[c], word, i + 1);
    }

    public boolean search(String word) {
        return search(ROOT, word, 0);
    }

    private boolean startsWith(TrieNode root, String prefix, int i) {
        if (i >= prefix.length()) return false;
        int c = prefix.charAt(i) - 'a';
        if (root.children[c] == null) return false;
        if (i == prefix.length() - 1) return true;
        return startsWith(root.children[c], prefix, i + 1);
    }

    public boolean startsWith(String prefix) {
        return startsWith(ROOT, prefix, 0);
    }

    public static void main(String[] args) {
        // ["app"],["apple"],["beer"],["add"],["jam"],["rental"]
        Trie obj = new Trie();
        obj.insert("app");
        obj.insert("apple");
        obj.insert("beer");
        obj.insert("add");
        obj.insert("jam");
        obj.insert("rental");
        System.out.println(obj.startsWith("apple"));
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}