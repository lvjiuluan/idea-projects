import com.lagou.service.IUserService;
import com.lagou.service.impl.UserServiceImpl;
import org.junit.Test;

public class SpringTest {
    @Test
    public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 获取到业务层对象
        IUserService userService = new UserServiceImpl();
        userService.save();
    }
}
