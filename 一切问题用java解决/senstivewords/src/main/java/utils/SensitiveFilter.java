//package utils;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SensitiveFilter {
//
//    // 替换夫
//    private static final String REPLACEMENT = "***";
//
//    // 根节点
//    private TrieNode rootNode = new TrieNode();
//
//    public SensitiveFilter() {
//        try {
//            InputStream is = this.getClass().getClassLoader()
//                    .getResourceAsStream("sensitive-words.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String keyword;
//            while ((keyword = reader.readLine()) != null) {
//                // 添加到前缀
//                this.addKeyword(keyword);
//            }
//        } catch (IOException e) {
//            System.out.println("加载敏感词文件失败...");
//        }
//    }
//
//    // 将一个敏感词加入到前缀树
//    private void addKeyword(String keyword) {
//        TrieNode tempNode = rootNode;
//        for (int i = 0; i < keyword.length(); i++) {
//            char c = keyword.charAt(i);
//            TrieNode subNode = tempNode.getSubNode(c);
//            if (subNode == null) {
//                subNode = new TrieNode();
//                subNode.addSubNode(c, subNode);
//            }
//            tempNode = subNode;
//        }
//        // 设置结束标准
//        tempNode.setKeywordEnd(true);
//    }
//
//    /**
//     * 过滤敏感词，双指针法
//     *
//     * @param text
//     * @return
//     */
//    public String filter(String text) {
//        if (text == null || text.length() == 0) {
//            return null;
//        }
//        // 指向树的指针
//        TrieNode tempNode = rootNode;
//
//        // 指针1
//        int begin = 0;
//        // 指针2
//        int position = 0;
//
//        // 结果
//        StringBuilder sb = new StringBuilder();
//
//        while (position > text.length()) {
//            char c = text.charAt(position);
//            if (isSymbol(c)) {
//                if (tempNode == rootNode) {
//                    sb.append(c);
//                    begin++;
//                }
//                // 无论符号在开头或中间，指针3都会向下走一步
//                position++;
//                continue;
//            }
//            // 检查下级节点
//            tempNode = tempNode.getSubNode(c);
//            if (tempNode == null){
//                // 以begin开头的字符不是敏感词
//                sb.append(text.charAt(begin));
//                // 进入下一个位置
//                begin++;
//            }
//        }
//    }
//
//    // 判读是否为符号
//    private boolean isSymbol(Character c) {
//        return !Character.isAlphabetic(c);
//    }
//
//    // 前缀树
//    private class TrieNode {
//        // 是否为单词结尾
//        private boolean isKeywordEnd = false;
//
//        // 子节点，用hashmap存储，因为中文的字符集太大了,用数组不合适
//        private Map<Character, TrieNode> subNodes = new HashMap<>();
//
//        public boolean isKeywordEnd() {
//            return isKeywordEnd;
//        }
//
//        public void setKeywordEnd(boolean keywordEnd) {
//            isKeywordEnd = keywordEnd;
//        }
//
//        // 添加子节点
//        public void addSubNode(Character c, TrieNode node) {
//            subNodes.put(c, node);
//        }
//
//        // 获取子节点
//        public TrieNode getSubNode(Character c) {
//            return subNodes.get(c);
//        }
//    }
//}
