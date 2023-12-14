import com.lagou.domain.Orders;
import com.lagou.domain.User;
import com.lagou.mapper.OrderMapper;
import com.lagou.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
    }

    @After
    public void after() {
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test1() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAll();
        for (User user : users) {
            System.out.println(user);
        }
//        mapper.save(users.get(0));
//        mapper.update(users.get(users.size() - 1));
//        mapper.delete(15);
    }

    /***
     * 一对一查询 订单信息以及所属用户
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = mapper.findAllWithUser();
        for (Orders order : orders) {
            System.out.println(order);
        }

    }

    /***
     * 查询所有用户 并查询关联的订单信息
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAllWithOrder();
        for (User user : users) {
            System.out.println(user+","+user.getOrdersList());
        }

    }

    /***
     * 多对多查询
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAllWithRole();
        for (User user : users) {
            System.out.println(user+","+user.getRoleList());
        }

    }
}
