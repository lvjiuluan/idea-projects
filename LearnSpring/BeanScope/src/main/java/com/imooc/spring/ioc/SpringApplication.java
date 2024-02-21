package com.imooc.spring.ioc;

import com.imooc.spring.ioc.controller.BookController;
import com.imooc.spring.ioc.entity.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext-controller.xml");
        System.out.println("=========IoC容器已初始化============");
        /*T t = new T(context);
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();*/
        Order order1 = context.getBean("order1", Order.class);
        order1.pay();
        System.out.println("=========业务代码执行完毕============");
        // 销毁IoC容器
        ((ClassPathXmlApplicationContext) context).registerShutdownHook();
    }
}

class T implements Runnable {

    ApplicationContext context;

    @Override
    public void run() {
        BookController bookController = context.getBean("bookController", BookController.class);
        bookController.addBook();
    }

    public T(ApplicationContext context) {
        this.context = context;
    }
}
