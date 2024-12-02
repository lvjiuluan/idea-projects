package 悟空并发编程.字典树.前缀树2;

import java.util.Arrays;

class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("ba");
        trie.printTR();
    }

    public void printTR(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 26; j++) {
                System.out.print(trie[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int N = 10;
    private static int index;
    private static int[][] trie = new int[N][26];
    private static int[] cnt = new int[N];

    // 在构造方法中完成重置 static 成员数组操作
    // 这样做的目的是为了减少 new 操作
    // 因为 OJ 每一个测试用例都会 new 一个新对象
    public Trie() {
        for (int row = index; row >= 0; row--) {
            Arrays.fill(trie[row], 0);
        }
        Arrays.fill(cnt,0);
        index = 0;
    }

    public void insert(String word) {
        int p = 0;
        for (int i = 0; i < word.length(); i++) {
            int x = word.charAt(i) - 'a';
            if (trie[p][x] == 0) {
                trie[p][x] = ++index;
            }
            p = trie[p][x];
        }
        cnt[p]++;
    }

    public boolean search(String word) {
        int p = 0;
        for (int i = 0; i < word.length(); i++) {
            int x = word.charAt(i) - 'a';
            if (trie[p][x] == 0) {
                return false;
            }
            p = trie[p][x];
        }
        return cnt[p] != 0;
    }

    public boolean startsWith(String prefix) {
        int p = 0;
        for (int i = 0; i < prefix.length(); i++) {
            int x = prefix.charAt(i) - 'a';
            if (trie[p][x] == 0) {
                return false;
            }
            p = trie[p][x];
        }
        return true;
    }

}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */