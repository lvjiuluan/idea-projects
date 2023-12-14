import com.lagou.domain.Account;
import com.lagou.proxy.CglibProxyFactory;
import com.lagou.proxy.JDKProxyFactory;
import com.lagou.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SpringTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JDKProxyFactory jdkProxyFactory;
    @Autowired
    private CglibProxyFactory cglibProxyFactory;

    @Test
    public void test1() {
        accountService.transfer("tom", "jerry", 100d);
    }

    @Test
    public void test2() {
        AccountService accountServiceJDKProxy = jdkProxyFactory.createAccountServiceJDKProxy();
        accountServiceJDKProxy.transfer("tom", "jerry", 100d);
    }

    @Test
    public void test3() {
        AccountService accountServiceJDKProxy = cglibProxyFactory.createAccountServiceJDKProxy();
        accountServiceJDKProxy.transfer("tom", "jerry", 100d);
    }

    @Test
    public void test4() {
        AccountService accountServiceJDKProxy = jdkProxyFactory.createAccountServiceJDKProxy();
        accountServiceJDKProxy.save(new Account());
    }
}
