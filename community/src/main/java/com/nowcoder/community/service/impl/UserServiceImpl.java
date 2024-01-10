package com.nowcoder.community.service.impl;

import com.google.gson.Gson;
import com.nowcoder.community.config.MailConfig;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.ActivationStatusEnum;
import com.nowcoder.community.service.IUserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import io.netty.util.internal.ResourcesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MailClient mailClient;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }


    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        // 1 对传进来的值进行空判断
        // 1.1 空处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        // 1.2 判断username是不是空串 null "" " "
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        // 1.3 判断password是不是空串
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        // 1.4 判断邮箱是不是空串
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        // 2 判断用户名、邮箱是否重复
        User tempUser = userMapper.selectByUsername(user.getUsername());
        if (tempUser != null) {
            map.put("usernameMsg", "该账号已存在！");
            return map;
        }
        tempUser = userMapper.selectByEmail(user.getEmail());
        if (tempUser != null) {
            map.put("emailMsg", "该邮箱已被注册！");
            return map;
        }
        // 3 注册用户，将用户的信息保存到数据库
        // 3.1 要将用户的密码加密，加密需要加盐处理
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0); // 默认普通用户
        user.setStatus(0); // 默认未激活
        user.setActivationCode(CommunityUtil.generateUUID()); // 激活码，随机字符串
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        userMapper.insertSelective(user);
        // 4 给用户发送激活邮件
        // 4.1 需要从thymeleaf模板引擎中获取html
        // 获取thymeleaf的Context
        Context context = new Context();
        // 将变量设置进去
        context.setVariable("email", user.getEmail());
        // 设置url http://127.0.0.1:8080/community/activation/101/activationCode
        // 把路径动态拼出来, 这里使用了mybatis主键回填，所以可以getId();
        // 需要设置userGeneratedKeys=true, keyProperty="主键名"
        String url = domain + contextPath + "/activation/"
                + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        // 4.2 使用thymeleaf模板引擎生成html放入content
        String content = templateEngine.process("/mail/activation", context);
        // 4.2 调用mailClient发送邮件
        mailClient.sendMail(user.getEmail(), "激活账号", content);
        // 5 返回空map，表示没问题
        return map;
    }

    @Override
    public Integer activation(Integer id, String activationCode) {
        // 1 根据id查询用户
        User user = userMapper.selectByPrimaryKey(id);
        // 2 判断是否已经激活
        if (user.getStatus() == 1) {
            return ActivationStatusEnum.REPEAT.getCode();
        }
        // 3 判断激活码对不对
        if (user.getActivationCode().equals(activationCode)) {
            user.setStatus(1);
            userMapper.updateByPrimaryKeySelective(user);
            return ActivationStatusEnum.SUCCESS.getCode();
        }
        return ActivationStatusEnum.FAILURE.getCode();
    }

    @Override
    public Map<String, Object> login(String username, String password, Long expiredSession) {
        Map<String, Object> map = new HashMap<>();
        // 1 对传进来的值进行空判断
        // 1.2 判断username是不是空串 null "" " "
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        // 1.3 判断password是不是空串
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        // 2 验证账号和密码是否正确，以及是否激活
        User tempUser = userMapper.selectByUsername(username);
        if (tempUser == null) {
            map.put("usernameMsg", "该账号不存在！");
            return map;
        }
        if (!tempUser.getStatus().equals(1)) {
            map.put("usernameMsg", "该账号未激活！");
            return map;
        }
        password = CommunityUtil.md5(password + tempUser.getSalt());
        if (!tempUser.getPassword().equals(password)) {
            map.put("passwordMsg", "密码错误！");
            return map;
        }
        // 3 可以进行登录，保存登录信息
        // 3.1 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(tempUser.getId());
        // 0-有效; 1-无效;
        loginTicket.setStatus(0);
        // 凭证是随机字符串
        loginTicket.setTicket(CommunityUtil.generateUUID());
        // 设置过期时间
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSession));
        // 4 保存loginTicket
//        loginTicketMapper.insertSelective(loginTicket);
        // 4.1 将保存loginTicket存入redis
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(loginTicket.getTicket(),
                new Gson().toJson(loginTicket));
        // 5 将ticket返回，需要存入cookie
        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    @Override
    public void logout(String ticket) {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        LoginTicket loginTicket = new Gson().fromJson(opsForValue.get(ticket), LoginTicket.class);
        if (opsForValue == null) {
            throw new RuntimeException("服务器错误");
        }
        // 0-有效; 1-无效;
        loginTicket.setStatus(1);
        // 修改状态后再保存
        opsForValue.set(ticket, new Gson().toJson(loginTicket));
    }

    @Override
    public void upload(String ticket, MultipartFile multipartFile) {
        /*
         * TODO 1 判断文件后缀名是否正确
         *      2 如何将本地文件夹变成服务器
         *      3 如何通过IO流将图片写进Response
         *      4 前端如果文件错误怎么报错，把输入密码那个错误提示复制上来
         *      5 用hostUser获取当前请求的用户
         *
         * 第二点本质上是 浏览器->http:127.0.0.1:8080/community/head/filename.jpg
         * ->controller写了一个@GetMapping("/head/{fileName}")来处理这个请求
         * -->通过HttpServletResponse返回给img标签
         *
         * */
        log.info("file = {}", multipartFile);
        // 通过ticket查找用户
        // 保存文件生成url
        // 用户设置headUrl
        // 更新用户
        try {
            URI resource = this.getClass().getResource("/static").toURI();
            String directoryPathStr = Paths.get(resource).toString();
            Path directoryPath = Paths.get(directoryPathStr, "upload");
            log.info("directoryPath = {}", directoryPath);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            String fileName = CommunityUtil.generateUUID() + multipartFile.getOriginalFilename();
            Path filePath = directoryPath.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), filePath);
            // 生成新的headerUrl
            // https://images.nowcoder.com/head/582t.png
            // http:127.0.0.1:8080/community/upload/filename.jpg
            // 获取当前项目的域名,contextPath,文件名，拼接成headerUrl
            String headerUrl = domain + contextPath + "/upload/" + fileName;
            log.info("headerUrl = {}", headerUrl);
            // 根据ticket去redis查询出user_Id
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            LoginTicket loginTicket = new Gson().fromJson(opsForValue.get(ticket), LoginTicket.class);
            User user = userMapper.selectByPrimaryKey(loginTicket.getUserId());
            // 修改url并保存
            user.setHeaderUrl(headerUrl);
            userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, Object> changePassword(String ticket, String original, String now) {
        Map<String, Object> map = new HashMap<>();
        // 将用户查出来
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        LoginTicket loginTicket = new Gson().fromJson(opsForValue.get(ticket), LoginTicket.class);
        User user = userMapper.selectByPrimaryKey(loginTicket.getUserId());
        String originalMd5 = CommunityUtil.md5(original + user.getSalt());
        if (!user.getPassword().equals(originalMd5)) {
            map.put("passwordMsg", "密码错误！");
            return map;
        }
        String nowMd5 = CommunityUtil.md5(now + user.getSalt());
        user.setPassword(nowMd5);
        userMapper.updateByPrimaryKeySelective(user);
        return map;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }


}
