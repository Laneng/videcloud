package com.videocloud.controller;

import com.videocloud.entity.Result;
import com.videocloud.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/17
 * Description：
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @RequestMapping("/uploadImage")
    @ResponseBody
    public Result uploadImage(MultipartFile file){
        String res = UploadUtil.upload(file);
        System.out.println(res);
        return new Result(0,"上传成功!",res,0);
    }

    @RequestMapping("/uploadVideo")
    @ResponseBody
    public Result uploadVideo(MultipartFile file) {
        System.out.println(new Date());
        String res = UploadUtil.upload(file);
        System.out.println(res);
        System.out.println(new Date());
        return new Result(0,"上传成功!",res,0);
    }
}
