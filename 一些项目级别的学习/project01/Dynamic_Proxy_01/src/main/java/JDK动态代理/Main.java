package JDK动态代理;

import java.lang.reflect.InvocationTargetException;

public class Main extends BigStar {

    public Main(String name) {
        super(name);
    }



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        BigStar bigStar = new BigStar("杨超越");
        Star starProxy = ProxyUtil.createProxy(bigStar);
        starProxy.sing("好日子");
        System.out.println("hhh");

    }
}
/*
 * starProxy $Rpxy0@528   $Rpxy0:JDK动态生成的代理类类名 @528对象内存地址
 * proxy     $Rpxy0@528
 *starProxy.sing ==> invoke(starProxy, sing的Method对象, sing的参数)
 *
 *
 * */