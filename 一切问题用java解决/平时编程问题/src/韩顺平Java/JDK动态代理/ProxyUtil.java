package 韩顺平Java.JDK动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyUtil {
    public static Star createProxy(BigStar bigStar) {
        Star starProxy = (Star) Proxy.newProxyInstance(
                ProxyUtil.class.getClassLoader(),
                new Class[]{Star.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                }
        );

        return starProxy;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ProxyUtil proxyUtil = new ProxyUtil();
        Method method = proxyUtil.getClass().getMethod("createProxy", BigStar.class);
        System.out.println(method);
    }
}
