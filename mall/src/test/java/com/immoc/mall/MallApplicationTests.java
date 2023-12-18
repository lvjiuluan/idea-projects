package com.immoc.mall;

import com.immoc.mall.dao.CategoryMapper;
import com.immoc.mall.pojo.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// 默认注解时不可以被继承的，除非注解被@Inherited元注解给注解了
@SpringBootTest  //可以被继承
@SuppressWarnings("all") // 不可被继承
public class MallApplicationTests {


}
