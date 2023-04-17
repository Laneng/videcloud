package com.videocloud.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 兰晓东
 * @create 2023-04-16 17:54
 * @describe:
 */
public class UploadUtil {

//    阿里域名
    public static final String ALI_DOMAIN = "https://jycz-view.oss-cn-beijing.aliyuncs.com/";


    public static String uploadImage(MultipartFile file)  {
//        获取源文件名
        String filename = file.getOriginalFilename();
//        获取文件后缀
        String ext = FilenameUtils.getExtension(filename);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newName = uuid + "." + ext;

//         地域节点
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tRj772MVTefTNa8Z8yF";
        String accessKeySercet = "AuDBCdhDGBukHeHLTED6lCGtgTQUTI";

//    OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySercet);
        try {
            ossClient.putObject("jycz-view",newName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }


        return ALI_DOMAIN + newName;
    }





}
