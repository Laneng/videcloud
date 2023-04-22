package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.*;
import com.videocloud.service.IUserService;
import com.videocloud.util.CodeUtil;
import com.videocloud.util.DBUtil;
import com.videocloud.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * @Author fdy
 * @Date 2023/4/18 20:41 星期二
 * @Description
 */

@Controller
@RequestMapping("email")
public class EmailController {

    private String code;

    @Autowired
    IUserService userService;



    /*
          1.发送邮箱
     */
    @ResponseBody
    @RequestMapping("sendEmail")
    public Result commonEmail(String email) throws Exception {


        code = CodeUtil.code();
        Pwd pwd = DBUtil.findAllById(1);

        String decode = PasswordUtil.decode(pwd.getPassword(), pwd.getKeyCode());

        String smtpHost = "smtp.163.com"; // SMTP服务器地址
        String email1 = pwd.getEmail(); // 发送方的邮箱地址
        String password = decode; // 发送方邮箱的授权码
        String toEmail = email; // 接收方的邮箱地址

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(properties);

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email1));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("基因重组视频平台验证码");
            message.setText("你个憨批，又把密码忘求了，再给你一次机会，记住你的验证码哈："+code);

            Transport transport = session.getTransport("smtp");
            transport.connect(smtpHost, email1, password);

            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Email sent successfully.");

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new Result(ResponseEnum.EMAIL_SUCCESS,0,null);
    }

    /*
          2.校验验证码
     */
    @ResponseBody
    @RequestMapping("code")
    public Result findPwdCode(String email,String identifyingCode){


        if (code.equals(identifyingCode)){
            System.out.println(code);
            return new Result(ResponseEnum.CODE_SUCCESS,0,null);
        }else {
            return new Result(ResponseEnum.CODE_FAIL,0,null);
        }

    }


    /*
      3.设置新密码
    */
    @ResponseBody
    @RequestMapping("pwd")
    public Result pwd(String email,String passWord){

        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("email", email);
        User user = userService.selectOne(wrapper);

        user.setPassword(passWord);
        boolean b = userService.updatePwd(user);

        return b?new Result(ResponseEnum.UPDATE_SUCCESS,0,null):new Result(ResponseEnum.UPDATE_FAIL,0,null);

    }


}
