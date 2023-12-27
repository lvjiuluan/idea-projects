package com.immoc.mall;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// 默认注解时不可以被继承的，除非注解被@Inherited元注解给注解了
@SpringBootTest  //可以被继承
@RunWith(SpringRunner.class)
@SuppressWarnings("all") // 不可被继承
public class MallApplicationTests {


}
