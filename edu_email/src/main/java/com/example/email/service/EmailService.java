package com.example.email.service;


import com.example.email.utils.RandomUtil;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {

    private static final String USER = "xxxxxx@qq.com"; // 发件人称号，同邮箱地址
    private static final String PASSWORD = "xxxxxx"; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     * 发送短信的方法
     * @param code
     * @param email
     * @return
     */
    public static boolean sendEmail(String code, String email) { //发送短信
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");

            //邮箱发送服务器端口,这里设置为587端口
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.port", "587");
            props.setProperty("mail.smtp.port", "587");


            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(email);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject("验证码");

            // 设置邮件的内容体
            message.setContent(code, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            System.out.println("发送成功");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    // public static void main(String[] args) throws Exception { // 做测试用
    //     String code = RandomUtil.getFourBitRandom(); //生成4位随机数
    //     System.out.println(code);
    //     EmailService.sendEmail("验证码："+code+"（有效时间为5分钟）","1443937284@qq.com");
    // }
}
