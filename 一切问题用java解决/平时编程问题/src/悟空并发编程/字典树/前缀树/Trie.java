package 悟空并发编程.字典树.前缀树;

public class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    private void _insert(TrieNode root, String s, int i) {
        if (i < s.length()) {
            int j = s.charAt(i) - 'a';
            if (root.next[j] == null) {
                root.next[j] = new TrieNode();
                if(i == s.length()-1){
                    root.next[j].tail = true;
                }
            }
            _insert(root.next[j], s, i + 1);
        }
    }
}

class TrieNode {
    public boolean tail = false;
    public TrieNode[] next = new TrieNode[26];
}
