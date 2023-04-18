package com.videocloud.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.exceptions.ClientException;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.util.FileUtil;
import com.videocloud.util.OSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/17
 * Description：
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @ResponseBody
    @RequestMapping
    public Result upload(MultipartFile file, HttpSession session) throws IOException, ClientException {

        File file1 = FileUtil.transferToFile(file);
        OSS ossClient = OSSUtil.getOSS(file);
        String newName = FileUtil.UUID(file);

        FileUtil.uploadSmallFile((OSSClient) ossClient,"jycz-view",newName, file1,session);

        return new Result(ResponseEnum.UPLOAD_SUCCESS,0,OSSUtil.ALI_DOMAIN+newName);
    }

    @RequestMapping("/exportStatus")
    @ResponseBody
    public Result Status(HttpServletRequest request){
        float exportStatus = (float) request.getSession().getAttribute("exportStatus");
        return new Result(ResponseEnum.SELECT_SUCCESS,1,exportStatus);
    }
}
