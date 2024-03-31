package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/*
 * 表示说我这个bean需要有spring容器来管理，
 * 而且使用@Component注解表示说是一个比较通用的bean，
 * 在哪些层次都可以使用。
 * */
@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    /*
     * 要使用的核心组件是JavaMailSender，这个注解已经由spring来管理了
     * 我们直接注入即可获取JavaMailSenderImpl实现类对象
     * */
    @Autowired
    private JavaMailSender mailSender;

    /*
     * 发送邮件需要几个条件：
     * 1、邮件发送方（由于是服务器发送邮件，所以这个是固定的）
     * 2、邮件接收方
     * 3、邮件主题
     * 4、邮件内容
     * */
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            /*
             * 这里通过message也可以实现setFrom，setTo等。
             * 但是MimeMessageHelper帮助我们进行一些基础功能的实现比如：
             * 邮箱地址检查是否满足格式、字符串是不是为空等等
             * */
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            /*
            * 表示运行支持html文本，将html转成字符传进来它也支持
            * */
            helper.setText(content, true);



            mailSender.send(helper.getMimeMessage());


        } catch (MessagingException e) {
            logger.error("发送邮件失败:" + e.getMessage());
        }
    }

}
