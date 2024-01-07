package 脚本;

public class Learn07 {
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("bf");
        trie.insert("be");
        StringBuilder sb = new StringBuilder();
        String words = "xwabfabcffbef";
        int i = 0;
        while (i < words.length()) {
            int j = i + 1;
            while (trie.startsWith(words.substring(i, j))) {
                j++;
            }
            j--;
            if (trie.search(words.substring(i, j))) {
                for (int k = i; k < j; k++) {
                    sb.append("*");
                }
                i = j;
            } else {
                sb.append(words.charAt(i));
                i++;
            }
        }
        System.out.println(sb);
    }
}
