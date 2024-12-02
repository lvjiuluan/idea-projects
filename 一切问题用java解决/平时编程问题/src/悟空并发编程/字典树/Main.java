package 悟空并发编程.字典树;

public class Main {
    static class TrieNode {
        TrieNode[] children = new TrieNode[2];
        int count = 0;
    }

    static TrieNode root = new TrieNode();

    static void insert(String str) {
        TrieNode node = root;
        for (char c : str.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
            node.count++;
        }
    }

    static String findKth(int k, int n) {
        return findKth(root, k, n, new StringBuilder());
    }

    static String findKth(TrieNode node, int k, int n, StringBuilder sb) {
        if (n == 0) {
            return sb.toString();
        }
        for (int i = 0; i < 2; i++) {
            if (node.children[i] != null) {
                if (node.children[i].count >= k) {
                    sb.append((char) (i + 'a'));
                    return findKth(node.children[i], k, n - 1, sb);
                } else {
                    k -= node.children[i].count;
                }
            }
        }
        return "-1";
    }

    public static void main(String[] args) {
        int n = 3, k = 5; // Change these values as per your requirement
        for (int i = 0; i < (1 << n); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = n - 1; j >= 0; j--) {
                sb.append((i & (1 << j)) > 0 ? 'b' : 'a');
            }
            insert(sb.toString());
        }
        System.out.println(findKth(k, n));
    }
}
