import com.lagou.config.SpringConfig;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // 指定JUNIT的运行环境
//@ContextConfiguration("{classpath:applicationContext.xml}") // 如果是xml开发 指定核心配置文件
@ContextConfiguration(classes = {SpringConfig.class}) // 如果是注解开发 指定核心配置类
public class SpringTest3 {
    @Autowired
    AccountService accountService;
    @Test
    public void test1() {
        List<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            System.out.println(account);
        }

    }
}
