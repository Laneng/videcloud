package com.videocloud.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Arrays;
import java.util.UUID;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/17
 * Description：
 */

public class FileUtil {



    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(","); //base64编码后的图片有头信息所以要分离出来   [0]data:image/png;base64, 图片内容为索引[1]
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(baseStrs[1]); //取索引为1的元素进行处理
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64DecodeMultipartFile(b, baseStrs[0]);//处理过后的数据通过Base64DecodeMultipartFile转换为MultipartFile对象
    }



    //将MultipartFile转换为File
    public static File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            if (filename[0].length() < 3){
                filename[0] = "temp"+filename[0];
            }
            file=File.createTempFile(filename[0], "."+filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //文件名随机
    public static String UUID(MultipartFile file){

        String filename = file.getOriginalFilename();
        //获取文件后缀
        String ext = FilenameUtils.getExtension(filename);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newName = uuid + "." + ext;
        return newName;
    }



    //OSS上传并每秒向Session中告知当前上传进度
    public static boolean uploadVideoFile(final OSSClient client, final String bucketName,
                                       final String key, final File uploadFile, HttpSession session)
            throws OSSException, ClientException, FileNotFoundException {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(uploadFile.length());
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("application/pdf");
        //对object进行服务器端加密。眼下服务器端仅仅支持x-oss-server-side-encryption加密
        objectMeta.setHeader("x-oss-server-side-encryption", "AES256");
        final InputStream input = new FileInputStream(uploadFile);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream tmpInput = null;
                while(true){
                    //将input缓存在tmpInput中，防止在调用available()方法是异常导致上传失败
                    tmpInput = input;
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        if(input!=null){
                            session.setAttribute("exportStatus",(float)(uploadFile.length()-tmpInput.available())/uploadFile.length());
                            if(tmpInput.available() == 0){
                                break;
                            }
                        }else{
                            break;
                        }
                    } catch (IOException e) {
                        break;
                    }
                }
                //通过获取oss上文件的大小来推断是否上传成功，假设不能从oss获得文件的大小说明上传失败
                try{
                    ObjectMetadata tmpObjectMeta = client.getObjectMetadata(bucketName, key);
                    session.setAttribute("exportStatus",(float)tmpObjectMeta.getContentLength()/uploadFile.length());
                    System.out.println(uploadFile.getName()+"的上传进度为："+tmpObjectMeta.getContentLength()/uploadFile.length());
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println(uploadFile.getName()+"上传失败");
                }
            }
        });
        t.start();
        PutObjectResult result =client.putObject(bucketName, key, input, objectMeta);

        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(uploadFile));
            System.out.println("MD5:"+ md5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("上传的object返回的E_tag："+result.getETag());
        return md5.equalsIgnoreCase(result.getETag());
    }




    public static boolean uploadImgFile(final OSSClient client, final String bucketName,
                                          final String key, final File uploadFile)
            throws OSSException, ClientException, FileNotFoundException {

        final InputStream input = new FileInputStream(uploadFile);

        PutObjectResult result =client.putObject(bucketName, key, input);

        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(uploadFile));
            System.out.println("MD5:"+ md5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("上传的object返回的E_tag："+result.getETag());
        return md5.equalsIgnoreCase(result.getETag());
    }


}
