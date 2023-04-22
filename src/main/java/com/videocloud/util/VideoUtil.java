package com.videocloud.util;

import com.coremedia.iso.IsoFile;

import java.io.IOException;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/22
 * Description：
 */

public class VideoUtil {



    /**
     * 获取视频文件的播放长度(mp4、mov格式)
     * @param videoPath
     * @return 单位为毫秒
     */
    public static long getMp4Duration(String videoPath) throws IOException {
        IsoFile isoFile = new IsoFile(videoPath);
        long lengthInSeconds =
                isoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                        isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        return lengthInSeconds;
    }


    /**
     * 得到语音或视频文件时长,单位秒
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath) throws IOException {
        String format = getVideoFormat(filePath);
        long result = 0;
        if("m4a".equals(format)) {
            result = VideoUtil.getMp4Duration(filePath);
        }else if("mov".equals(format)){
            result = VideoUtil.getMp4Duration(filePath);
        }else if("mp4".equals(format)){
            result = VideoUtil.getMp4Duration(filePath);
        }

        return result;
    }

    /**
     * 得到语音或视频文件时长,单位秒
     * @param filePath
     * @return
     * @throws IOException
     */
    public static long getDuration(String filePath,String format) throws IOException {
        long result = 0;
        if("m4a".equals(format)) {
            result = VideoUtil.getMp4Duration(filePath);
        }else if("mov".equals(format)){
            result = VideoUtil.getMp4Duration(filePath);
        }else if("mp4".equals(format)){
            result = VideoUtil.getMp4Duration(filePath);
        }

        return result;
    }


    /**
     * 得到文件格式
     * @param path
     * @return
     */
    public static String getVideoFormat(String path){
        return  path.toLowerCase().substring(path.toLowerCase().lastIndexOf(".") + 1);
    }


}


