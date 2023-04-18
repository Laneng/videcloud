package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.Email;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.IUserService;
import com.videocloud.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource
    private JavaMailSender mailSender;

    //	获得.yml文件发件人信息
    @Value("${spring.mail.username}")
    private String from;


    /*
          1.发送邮箱
     */
    @ResponseBody
    @RequestMapping("sendEmail")
    public Result commonEmail(String email) {

        code = CodeUtil.code();

        //创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置发件人
        message.setFrom(from);
        // 设置收件人
//        message.setTo(Email.getTo());
        message.setTo(email);
        // 设置验证码
       message.setText(code);

        // 发送邮件
        mailSender.send(message);

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
