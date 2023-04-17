package com.videocloud.controller;

import com.videocloud.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public String uploadImage(MultipartFile file){
        return UploadUtil.upload(file);
    }

    @RequestMapping("/uploadVideo")
    public String uploadVideo(MultipartFile file) {
        return UploadUtil.upload(file);
    }
}
