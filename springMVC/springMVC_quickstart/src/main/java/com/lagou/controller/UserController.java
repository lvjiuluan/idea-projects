package com.lagou.controller;

import com.lagou.domain.QueryVo;
import com.lagou.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller // 将该类的生成对象权力交给Spring
@RequestMapping("/user")
@SessionAttributes("username")
public class UserController {
    @RequestMapping("/quick")
    public String quick() {
        // 业务逻辑
        System.out.println("springMVC入门成功...");
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/simpleParam")
    public String simpleParam(Integer id, String username) {
        // 业务逻辑
        System.out.println(id);
        System.out.println(username);
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/pojoParam")
    public String pojoParam(User user) {
        // 业务逻辑
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/arrayParam")
    public String arrayParam(Integer[] ids) {
        // 业务逻辑
        System.out.println(Arrays.toString(ids));
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/queryParam")
    public String queryParam(QueryVo queryVo) {
        // 业务逻辑
        System.out.println(queryVo);
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/converterParam")
    public String converterParam(Date birthday) {
        // 业务逻辑
        System.out.println(birthday);
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/findByPage")
    public String findByPage(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "32") Integer pageSize) {
        // 业务逻辑
        System.out.println(pageNum);
        System.out.println(pageSize);
        // 视图跳转 逻辑视图名
        return "success";
    }

    @RequestMapping("/requestHeader")
    public String requestHead(@RequestHeader("cookie") String cookie) {
        // 业务逻辑
        System.out.println(cookie);
        // 视图跳转 逻辑视图名
        return "success";
    }

    // 获取cookieValue
    @RequestMapping("/cookieValue")
    public String cookieValue(@CookieValue("JSESSIONID") String jsessionid) {
        // 业务逻辑
        System.out.println(jsessionid);
        // 视图跳转 逻辑视图名
        return "success";
    }

    // 获取原始servlet API
    @RequestMapping("/servletAPI")
    public String servletAPI(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 业务逻辑
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
        // 视图跳转 逻辑视图名
        return "success";
    }

    // 获取原始servlet API
    @RequestMapping("/returnVoid")
    public void returnVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 业务逻辑
//        request.getRequestDispatcher("/webapp/WEB-INF/pages/success.jsp").forward(request,response);
//        response.sendRedirect(request.getContextPath() + "/index.jsp");
        // 视图跳转 逻辑视图名
        //返回数据
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("拉勾网");
//        return "success";
    }

    // 转发 一次请求
    @RequestMapping("/forward")
    public String forward(Model model) {
        // 业务逻辑
        model.addAttribute("username", "拉勾招聘");
        // 视图跳转 逻辑视图名
        // 既可以转到jsp，也可以转到其它控制器方法
        return "forward:/WEB-INF/pages/success.jsp";
    }

    // 重定向 两次请求
    @RequestMapping("/redirect")
    public String redirect(Model model) {
        // 业务逻辑
        // 底层是request.setAttribute, 使用重定向超出其声明周期了
        model.addAttribute("username", "拉勾招聘");
        // 视图跳转 逻辑视图名
        // 既可以转到jsp，也可以转到其它控制器方法
        return "redirect:/index.jsp";  //这种方式不会走视图解析器  底层是request.sendRedirect和...
    }

    @RequestMapping("/returnModelAndView")
    public ModelAndView returnModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", "modelAndView方式一");
        // 设置要跳转的视图  走视图解析器
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping("/returnModelAndView2")
    public ModelAndView returnModelAndView2(ModelAndView modelAndView) {
        modelAndView.addObject("username", "modelAndView方式二");
        // 设置要跳转的视图  走视图解析器
        modelAndView.setViewName("success");
        return modelAndView;
    }

    // ajax异步交互
    // '[{"id":1,"username":"张三"},{"id":2,"username":"李四"}]'
    @RequestMapping("/ajaxRequest")
    @ResponseBody  //会将return返回给前端，而不是视图解析器
    public List<User>  ajaxRequest(@RequestBody List<User> userList) {
        System.out.println(userList);
        return userList;
    }
}
