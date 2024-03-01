package JDK动态代理;

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

                        // 代理对象要做的事
                        if (method.getName().equals("sing")) {
                            System.out.println("准备话筒，收钱二十万");
                        } else if (method.getName().equals("dance")) {
                            System.out.println("准备场地，收钱一千万");
                        }
                        return method.invoke(bigStar, args);
                    }
                }
        );
        return starProxy;
    }
}
