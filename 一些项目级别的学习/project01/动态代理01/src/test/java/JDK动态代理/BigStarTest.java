package JDK动态代理;


import org.junit.Test;

public class BigStarTest {
    @Test
    public void test01() {
        BigStar bigStar = new BigStar("杨超越");
        Star starProxy = ProxyUtil.createProxy(bigStar);
        starProxy.sing("好日子");
    }
}