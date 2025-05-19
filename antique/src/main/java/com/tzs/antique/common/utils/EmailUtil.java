package com.tzs.antique.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class EmailUtil {

    @Value("${mail.fromurl}")
    private  String fromurl;

    @Value("${mail.password}")
    private  String password;

    @Value("${mail.smtphost}")
    private  String smtphost;

    @Value("${mail.smtpport}")
    private  String smtpport;

    @Value("${mail.smtpauth}")
    private  String smtpauth;

    @Value("${mail.starttlsenable}")
    private  String starttlsenable;


    /***
     *  发送邮件
     */
    public  Boolean sendEmail(String email, String title, String content){

        // 设置属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtphost); // 设置SMTP服务器
        properties.setProperty("mail.smtp.port", smtpport); // 设置端口
        properties.setProperty("mail.smtp.auth", smtpauth); // 需要认证
        properties.setProperty("mail.smtp.starttls.enable", starttlsenable); // 启用TLS加密

        // 获取默认的Session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromurl, password); // 设置发件人邮箱和授权码
            }
        });

        try {
//            session.setDebug(true);
            // 创建MimeMessage对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(fromurl));
            // 设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 设置邮件主题
            message.setSubject(title);
            // 设置邮件正文
            message.setText(content);
            // 发送邮件
            Transport.send(message);
            log.info("邮件发送成功：" + content);
            return true;
        } catch (MessagingException e) {
            log.info("邮件发送失败：" + e.getMessage());
            return false;
        }
    }
}
