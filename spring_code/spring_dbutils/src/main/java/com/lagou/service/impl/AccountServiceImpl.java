package com.lagou.service.impl;

import com.lagou.dao.AccountDao;
import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*  <!--    <bean class="com.lagou.service.impl.AccountServiceImpl" id="accountService">-->
    <!--        <property name="accountDao" ref="accountDao"></property>-->
    <!--    </bean>-->
*/

@Service("accountService") // 相当于配置了bean标签
public class AccountServiceImpl implements AccountService {
    @Autowired  // 根据类型(AccountDao)进行注入，spring自动去IOC map里面找对应类型的对象 相当于property
//    @Qualifier("accountDao") // 当匹配到多个实例对象时再根据IOC map的name值去找
//    @Resource(name = "accountDao") // 直接匹配IOC map name
    private AccountDao accountDao;

    @Value("注入普通属性")
    private String str;

    @Value("${jdbc.driverClassName}")
    private String driver;

    @Override
    public List<Account> findAll() {
        System.out.println(str + driver);
        return accountDao.findAll();
    }

    @Override
    public Account findById(int id) {
        return accountDao.findById(id);
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(int id) {
        accountDao.delete(id);
    }

}
