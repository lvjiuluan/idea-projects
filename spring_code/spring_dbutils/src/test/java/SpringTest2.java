import com.lagou.config.SpringConfig;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class SpringTest2 {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    AccountService accountService = applicationContext.getBean("accountService", AccountService.class);

    @Test
    public void test1() {
        List<Account> accounts = accountService.findAll();
        for (Account account : accounts) {
            System.out.println(account);
        }

    }

    @Test
    public void test2() {
        Account account = new Account();
        account.setMoney(12.2);
        account.setName("字幕");
        accountService.save(account);
        test1();

    }

    @Test
    public void test3() {
        Account account = accountService.findById(3);
        System.out.println(account);
    }

    @Test
    public void test4() {
        Account account = accountService.findById(3);
        account.setName("zimu");
        accountService.update(account);
        test1();
    }

    @Test
    public void test5() {
        accountService.delete(3);
        test1();
    }
}
