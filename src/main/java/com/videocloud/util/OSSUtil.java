package com.videocloud.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.http.HttpRequest;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 兰晓东
 * @create 2023-04-16 17:54
 * @describe:
 */
public class OSSUtil {

//    阿里域名
    public static final String ALI_DOMAIN = "https://jycz-view.oss-cn-beijing.aliyuncs.com/";

    public static OSS getOSS(MultipartFile file)  {

//         地域节点
//        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String endpoint = "oss-accelerate.aliyuncs.com";
        String accessKeyId = "LTAI5tRj772MVTefTNa8Z8yF";
        String accessKeySercet = "AuDBCdhDGBukHeHLTED6lCGtgTQUTI";

//    OSS客户端对象
        return  new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySercet);
    }
}
