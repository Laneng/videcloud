package com.videocloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.User;
import com.videocloud.service.IUserService;
import com.videocloud.util.FileUtil;
import com.videocloud.util.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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


    /*
          登录
    */
    @ResponseBody
    @RequestMapping("/login")
    public Result login(String loginName, String passWord, HttpSession httpSession){


        User user = userService.login(loginName, passWord);

        if (user != null){
            if (user.getStatus().equals("封禁")){
                return new Result(ResponseEnum.STATUS_STOP,0,null);
            }else if (user.getStatus().equals("")){
                user.setStatus("正常");

                userService.updateById(user);

                httpSession.setAttribute("user",user);
                return new Result(ResponseEnum.LOGIN_SUCCESS,0,null);

            }else if (user.getStatus().equals("正常")){
                httpSession.setAttribute("user",user);
                return new Result(ResponseEnum.LOGIN_SUCCESS, 0, null);
            }else {
                return new Result(ResponseEnum.LOGIN_SUCCESS, 0, null);
            }
        }else if (user == null){
            return new Result(ResponseEnum.LOGIN_FAIL,0,null);
        }else {
            return new Result(ResponseEnum.LOGIN_FAIL,0,null);
        }

    }

        /*
            登出
         */
    @ResponseBody
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        // 获取当前session
        HttpSession session = request.getSession();


        if (session.getAttribute("user") == null){
            return new Result(ResponseEnum.LOGOUT_FAIL,0,null);

        }else {
            session.removeAttribute("user");
            return new Result(ResponseEnum.LOGOUT_SUCCESS,0,null);
        }



    }

    //查询注册时的填写的邮箱账号是否已经存在
    @ResponseBody
    @RequestMapping("/getEmail")
    public Result ifNullPhone(String loginName){

        System.out.println(loginName);
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("email", loginName);
        User user = userService.selectOne(wrapper);

        if (user == null ){//账号不存在
            return new Result(ResponseEnum.REGISTER_FPHONE,0,null);
        }else {
            return new Result(ResponseEnum.REGISTER_TPHONE,0,null);
        }
    }


    //注册
    @ResponseBody
    @RequestMapping("/register")
    public Result register(String loginName, String passWord,HttpSession httpSession){
        User user = userService.register(loginName, passWord);

        if(user!=null){
            user.setAvatar("https://jycz-view.oss-cn-beijing.aliyuncs.com/b1800e92caa4425aad66738eea2fc09a.jpg");
            user.setStatus("正常");
            user.setRtime(new Date());
            user.setName(UUID.randomUUID().toString().replace("-"," ").substring(5));
            user.setMsg("这个人很懒，什么也没留下~~~");
            userService.updateById(user);
            httpSession.setAttribute("user",user);
        }

        return  user!=null?new Result(ResponseEnum.REGISTER_SUCCESS,1,null):new Result(ResponseEnum.REGISTER_FAIL,0,null);

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

        boolean b = FileUtil.uploadImgFile((OSSClient) ossClient, "jycz-view", newName, file);

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





    /*
         管理员查询所有用户信息
    */

    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAll(Integer page,Integer limit){


        return  userService.getAll(page, limit);

    }
    /*
            管理员查询正常用户信息
       */
    @RequestMapping("/getNormal")
    @ResponseBody
    public Result getNormal(Integer page,Integer limit){


        return  userService.getNormal(page, limit);

    }
    /*
            管理员查询封禁用户信息
       */
    @RequestMapping("/getStop")
    @ResponseBody
    public Result getStop(Integer page,Integer limit){


        return  userService.getStop(page, limit);

    }


    /*
        管理员修改用户状态：正常|封禁
   */
    @ResponseBody
    @RequestMapping("/status")
    public Result upStatus(Integer id,String status){
        System.out.println("状态："+status);
           return  userService.upStatus(id,status);
    }



}


