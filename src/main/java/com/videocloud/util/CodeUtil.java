package com.videocloud.util;

import java.util.Random;

/**
 * @Author fdy
 * @Date 2023/4/18 21:57 星期二
 * @Description
 */
public class CodeUtil {


    public static String code(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result+=random.nextInt(10);
        }
        System.out.println(result);

        return result;
    }


}
