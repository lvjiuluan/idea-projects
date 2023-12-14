import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Test
    public void test01(){
        for (Account account : accountService.findAll()) {
            System.out.println(account);
        }
    }
    @Test
    public void test02(){
        Account account = new Account();
        account.setName("lucy");
        account.setMoney(500d);
        accountService.save(account);
        test01();
    }
    @Test
    public void test03(){
        Account account = accountService.findById(3);
        account.setName("lucy");
        account.setMoney(1000d);
        accountService.update(account);
        test01();
    }
    @Test
    public void test04(){
        accountService.delete(4);
        test01();
    }
}
