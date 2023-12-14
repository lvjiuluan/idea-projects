package com.lagou.controller;

import com.lagou.domain.Account;
import com.lagou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    // 查询所有账户
    @RequestMapping("findAll")
    public String findAll(Model model) {
        // 实现查询所有账户
        List<Account> list = accountService.findAll();

        model.addAttribute("list", list);

        return "list";
    }

    // 添加账户
    @RequestMapping("/save")
    public String save(Account account) {
        // 业务逻辑
        System.out.println(account.getName());
        accountService.save(account);
        // 跳转到findAll方法,重新查询
        // 两次请求
        return "redirect:/account/findAll";
    }

    // 根据id查询账户，完成账户回显
    @RequestMapping("/findById")
    public String findById(Integer id,Model model) {
        // 业务逻辑
        Account account = accountService.findById(id);
        model.addAttribute("account",account);
        // 跳转到findAll方法,重新查询
        return "update";
    }

    // 根据id查询账户，完成账户回显
    @RequestMapping("/update")
    public String update(Account account) {
        // 业务逻辑
        accountService.update(account);
        // 两次请求
        return "redirect:/account/findAll";
    }

    // 批量删除
    @RequestMapping("/deleteBatch")
    public String deleteBatch(Integer[] ids) {
        // 业务逻辑
        accountService.deleteBatch(ids);
        return "redirect:/account/findAll";
    }
}
