package 悟空并发编程.字典树.树节点;

class Trie {

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            int x = word.charAt(i) - 'a';
            if (p.next[x] == null){
                p.next[x] = new TrieNode();
            }
            p = p.next[x];
        }
        p.end = true;
    }

    public boolean search(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); i++) {
            int x = word.charAt(i) - 'a';
            if (p.next[x] == null){
                return false;
            }
            p = p.next[x];
        }
        return p.end;
    }

    public boolean startsWith(String prefix) {
        TrieNode p = root;
        for (int i = 0; i < prefix.length(); i++) {
            int x = prefix.charAt(i) - 'a';
            if (p.next[x] == null){
                return false;
            }
            p = p.next[x];
        }
        return true;
    }
}

class TrieNode{
    boolean end;
    TrieNode[] next = new TrieNode[26];
}



/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
