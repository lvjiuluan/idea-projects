import com.lagou.dao.IUserDao;
import com.lagou.dao.impl.UserDaoImpl;
import com.lagou.dao.impl.UserDaoImpl2;
import com.lagou.service.IUserService;
import com.lagou.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringTest {
    @Test
    public void test1() {
        // 一开始就创建对象放到容器中  类路径下开始加载
        // 磁盘路径加载文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
        userDao.save();
    }

    @Test
    public void test2() {
        BeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        // 这时候才创建对象放到容器中
        IUserDao userDao = (IUserDao) xmlBeanFactory.getBean("userDao");
        userDao.save();
    }

    @Test
    public void test3() {
        // 一开始就创建对象放到容器中  类路径下开始加载
        // 磁盘路径加载文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDao userDao = applicationContext.getBean("userDao2", IUserDao.class);
        userDao.save();
    }

    @Test
    public void test4() {
        // 一开始就创建对象放到容器中  类路径下开始加载
        // 磁盘路径加载文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserDao userDao1 = applicationContext.getBean("userDao", IUserDao.class);
        IUserDao userDao2 = applicationContext.getBean("userDao", IUserDao.class);
        System.out.println(userDao1 == userDao2);
    }

    @Test
    public void test5() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        userService.save();
    }
}
