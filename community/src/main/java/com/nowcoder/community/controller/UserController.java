package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.ActivationStatusEnum;
import com.nowcoder.community.form.LoginForm;
import com.nowcoder.community.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import static com.nowcoder.community.enums.LoginTicketExpiredEnum.DEFALUT_EXPIRED_SECONDS;
import static com.nowcoder.community.enums.LoginTicketExpiredEnum.REMEMBERME_EXPIRED_SECONDS;

@Controller
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private Producer kaptchaProducer;

    // 1 处理访问注册页面的请求
    @GetMapping("/register")
    public String getRegisterPage() {
        return "site/register";
    }

    // 2 提交信息进行注册的请求
    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map.isEmpty()) {
            // 提示已经注册成功
            // 跳到中间页面 operate-result
            model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            // 注册失败
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            // user作为方法参数会自动加入到model里面
            return "site/register";
        }
    }

    // /activation/101/activationCode
    @GetMapping("/activation/{id}/{activationCode}")
    public String activate(Model model,
                           @PathVariable("id") Integer id,
                           @PathVariable("activationCode") String activationCode) {
        Integer result = userService.activation(id, activationCode);
        if (result.equals(ActivationStatusEnum.SUCCESS.getCode())) {
            // 激活成功
            model.addAttribute("msg", ActivationStatusEnum.SUCCESS.getDesc());
            // 跳到登录页面
            model.addAttribute("target", "/login");
        }
        if (result.equals(ActivationStatusEnum.REPEAT.getCode())) {
            // 重复激活 提示信息
            model.addAttribute("msg", ActivationStatusEnum.REPEAT.getDesc());
            // 跳到首页 target
            model.addAttribute("target", "/index");
        }
        if (result.equals(ActivationStatusEnum.FAILURE.getCode())) {
            // 激活失败 提示信息
            model.addAttribute("msg", ActivationStatusEnum.FAILURE.getDesc());
            // 跳到首页 target
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse httpServletResponse,
                           HttpSession httpSession) {
        // 1 生成验证码字符串和图片
        String text = kaptchaProducer.createText();
        BufferedImage bufferedImage = kaptchaProducer.createImage(text);
        // 2 用httpServletResponse将图片设置,将图片输出给浏览器
        httpServletResponse.setContentType("image/png");
        try {
            ServletOutputStream os = httpServletResponse.getOutputStream();
            ImageIO.write(bufferedImage, "png", os);
        } catch (IOException e) {
            log.error("相应验证码失败失败" + e.getMessage());
        }
        // 3 用httpSession将验证码字符串保存
        httpSession.setAttribute("kaptcha", text);
    }

    @GetMapping("/login")
    public String login() {
        return "/site/login";
    }

    @PostMapping("/login")
    public String login(Model model, LoginForm loginForm,
                        HttpSession httpSession,
                        HttpServletResponse response) {
        log.info("loginForm = {}", loginForm);
        // 1 首先判断验证码对不对
        // 送session中取出来
        String kaptcha = (String) httpSession.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) ||
                StringUtils.isBlank(loginForm.getValidCode()) ||
                !kaptcha.equalsIgnoreCase(loginForm.getValidCode())) {

            model.addAttribute("codeMsg", "验证码不正确");
            // 回到登录页面
            return "/site/login";
        }
        // 2 判断用户名和密码
        // 如果勾上记住我就过期时间长一点
        Long expiredSession = loginForm.getRememberMe() ? REMEMBERME_EXPIRED_SECONDS.getCode() : DEFALUT_EXPIRED_SECONDS.getCode();
        Map<String, Object> map = userService.login(loginForm.getUsername(), loginForm.getPassword(), expiredSession);
        // 判断是否包含ticket
        if (map.containsKey("ticket")) {
            // 表示登录成功
            // cookie带上ticke
            Cookie cookie = new Cookie("ticket", (String) map.get("ticket"));
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSession.intValue());
            response.addCookie(cookie);
            // 重定向到首页, 这会发起一次新的请求，搞清楚为什么很重要，凭什么
            // 如果直接return "index"; 则浏览器窗口中的url不会改变，不应该这样
            // 会发起两次请求
            return "redirect:/index";
        } else {
            // 表示有错误
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            // 回到登录页面
            //  还是同一次请求
            return "site/login";
        }
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:index";
    }

    // 上传用户头像
    @PostMapping("/upload")
    public void upload(MultipartFile multipartFile) {
        userService.upload("", multipartFile);
    }
}
