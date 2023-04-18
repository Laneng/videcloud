package com.videocloud.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.exceptions.ClientException;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.IUserService;
import com.videocloud.util.FileUtil;
import com.videocloud.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fdy
 * @since 2023-04-17
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public Result login(String loginName, String passWord, HttpSession httpSession){
        User user = userService.login(loginName, passWord);
        System.out.println(user);

        if(user!=null){
            httpSession.setAttribute("user",user);
            Result result = new Result(ResponseEnum.LOGIN_SUCCESS, 1, user);
            return result;
        }else {
            Result result = new Result(ResponseEnum.LOGIN_FAIL, 0, null);

            return result;
        }

    }

    public Result register(){
        return  null;
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Result userSaveOrUpdate(User user,HttpSession session){
        boolean b = userService.saveOrUpdate(user);
        if (b){
            User user1 = userService.getById(user.getId());
            session.setAttribute("user",user1);
            return new Result(ResponseEnum.UPDATE_SUCCESS,1,null);
        }else{
            return new Result(ResponseEnum.UPDATE_FAIL,0,null);
        }
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Result changePassword(Integer id,String password,String newPassword,HttpSession session){

        User user = userService.getById(id);

        if (user.getPassword().equals(password)){

            user.setPassword(newPassword);
            boolean b = userService.updateById(user);

            if (b){
                session.setAttribute("user",user);
                return new Result(ResponseEnum.UPDATE_SUCCESS,0,b);
            }else {
                return new Result(ResponseEnum.UPDATE_FAIL,0,null);
            }
        }else {
            return new Result(ResponseEnum.WRONG_PASSWORD,0,null);
        }

    }

    @RequestMapping("/changeAvatar")
    @ResponseBody
    public Result changeAvatar(User user,HttpSession session) throws FileNotFoundException, ClientException {

        String base64 = user.getAvatar();
        MultipartFile multipartFile = FileUtil.base64Convert(base64);
        File file = FileUtil.transferToFile(multipartFile);
        OSS ossClient = OSSUtil.getOSS(multipartFile);
        String newName = FileUtil.UUID(multipartFile);

        boolean b = FileUtil.uploadSmallFile((OSSClient) ossClient, "jycz-view", newName, file, session);

        if(b){
            user.setAvatar(OSSUtil.ALI_DOMAIN+newName);
            boolean b1 = userService.updateById(user);
            if(b1){
                User user1 = userService.getById(user.getId());
                session.setAttribute("user",user1);
                return new Result(ResponseEnum.UPDATE_SUCCESS,1,b1);
            }
        }
        return new Result(ResponseEnum.UPDATE_FAIL,0,null);
    }
}


