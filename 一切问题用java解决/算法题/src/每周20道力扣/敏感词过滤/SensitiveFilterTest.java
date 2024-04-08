package 每周20道力扣.敏感词过滤;

public class SensitiveFilterTest {
    public static void main(String[] args) {
        SensitiveFilter sensitiveFilter = new SensitiveFilter();
        sensitiveFilter.init();
        String s = sensitiveFilter.replaceSensitiveWords("我是习进>平的李强的它们的大佬");
        System.out.println(s);
    }
}
